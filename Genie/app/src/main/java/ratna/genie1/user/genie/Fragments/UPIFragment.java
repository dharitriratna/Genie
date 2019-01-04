package ratna.genie1.user.genie.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ratna.genie1.user.genie.MoneyTransfer.SendPaymentActivity;
import ratna.genie1.user.genie.R;

import ratna.genie1.user.genie.MoneyTransfer.SendPaymentActivity;


public class UPIFragment extends Fragment implements View.OnClickListener {
    private TextView phoneTv;
    private EditText nameEd;
    private Button continueBtn;
    private static final int PICK_CONTACT = 1995;
    public UPIFragment() {
        // Required empty public constructor
    }


    public static UPIFragment newInstance(String param1, String param2) {
        UPIFragment fragment = new UPIFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(ratna.genie1.user.genie.R.layout.fragment_upi, container, false);
        phoneTv=(TextView)v.findViewById(ratna.genie1.user.genie.R.id.phoneTv);
        nameEd=(EditText)v.findViewById(ratna.genie1.user.genie.R.id.nameEd);
        continueBtn=(Button)v.findViewById(ratna.genie1.user.genie.R.id.continueBtn);
        phoneTv.setOnClickListener(this);
        continueBtn.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case ratna.genie1.user.genie.R.id.phoneTv:
                Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(intent, PICK_CONTACT);
                break;
            case ratna.genie1.user.genie.R.id.continueBtn:
                startActivity(new Intent(getContext(), SendPaymentActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //       Toast.makeText(MobileRecharge.this, "Number="+num, Toast.LENGTH_LONG).show();
                                nameEd.setText(num);
                            }
                        }
                    }
                    break;
                }
        }
    }
}
