package ratna.genie1.user.genie;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ratna.genie1.user.genie.Fragments.FlightBottomFragment;
import ratna.genie1.user.genie.MoneyTransfer.MoneyTransferActivity;
import ratna.genie1.user.genie.helper.DatePickerFragmentFrom;
import ratna.genie1.user.genie.helper.ItemClickListener;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.Calendar;

import ratna.genie1.user.genie.helper.DatePickerFragmentFrom;
import ratna.genie1.user.genie.helper.ItemClickListener;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class FlightActivity extends AppCompatActivity implements View.OnClickListener,ItemClickListener {
    Toolbar toolbar;
    EditText depEd,arrivalEd,fromEd,toEd,travellerEd,classEd,deppEd;
    private boolean arrive=false;
    private int depYear,depMonth,depDay;
    private Button flightBtn;
    private RadioButton onewayRB,roundtrip;
    private RelativeLayout depRel,arrRel;

    FlightBottomFragment flightBottomFragment;
    String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(FlightActivity.this).getUserGroup();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupId.equals("4")){
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    finish();
                }
                else if (groupId.equals("5")){
                    startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                    finish();
                }
                else if (groupId.equals("3")){
                    startActivity(new Intent(getApplicationContext(),MainActivity4.class));
                    finish();
                }
                else if (groupId.equals("2")){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });

        intialize();



        //showFlightDatePicker();
    }
    private void intialize(){
        depEd=findViewById(R.id.depEd);
        depEd.setOnClickListener(this);
        arrivalEd=findViewById(R.id.arrivalEd);
        arrivalEd.setOnClickListener(this);
        toEd=findViewById(R.id.toEd);
        fromEd=findViewById(R.id.fromEd);
        fromEd.setOnClickListener(this);
        toEd.setOnClickListener(this);
        travellerEd=findViewById(R.id.travellerEd);
        travellerEd.setOnClickListener(this);
        classEd=findViewById(R.id.classEd);
        classEd.setOnClickListener(this);
        flightBtn=findViewById(R.id.flightBtn);
        flightBtn.setOnClickListener(this);
        onewayRB=findViewById(R.id.onewayRB);
        roundtrip=findViewById(R.id.roundtrip);
        onewayRB.setOnClickListener(this);
        roundtrip.setOnClickListener(this);
        deppEd=findViewById(R.id.deppEd);
        deppEd.setOnClickListener(this);
        depRel=findViewById(R.id.depRel);
        arrRel=findViewById(R.id.arrRel);

    }

    public void showFlightDatePicker(){
        DatePickerFragmentFrom date = new DatePickerFragmentFrom();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondatefrom);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondatefrom = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
                depYear=year;
                depMonth=monthOfYear;
                depDay=dayOfMonth;

            String datemain_from=String.valueOf(dayOfMonth)+ "/" +String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);

                depEd.setText(datemain_from);


        }
    };
    public void showFlightArrivalDatePicker(){
        DatePickerFragmentFrom date = new DatePickerFragmentFrom();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("year", depYear);
        args.putInt("month", depMonth);
        args.putInt("day", depDay);
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondatefromArrival);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondatefromArrival = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String datemain_from=String.valueOf(dayOfMonth)+ "/" +String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                if(monthOfYear<depMonth){
                    Toast.makeText(getApplicationContext(),"Please Select Proper Date",Toast.LENGTH_SHORT).show();
                }else {
                    if((monthOfYear==depMonth)){
                        if(dayOfMonth<depDay){
                            Toast.makeText(getApplicationContext(),"Please Select Proper Date",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            arrivalEd.setText(datemain_from);
                        }
                    }else {
                        arrivalEd.setText(datemain_from);
                    }

                }


        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.depEd:
                arrive=false;

                showFlightDatePicker();
                if(depEd.getText().toString().isEmpty()){
                    arrivalEd.setEnabled(true);
                }
                else {
                    arrivalEd.setEnabled(false);
                }

                break;
            case R.id.arrivalEd:
                arrive=true;
                arrivalEd.setEnabled(true);
                showFlightArrivalDatePicker();
                break;
            case R.id.fromEd:
                RegPrefManager.getInstance(this).setPlace("From");
                startActivity(new Intent(FlightActivity.this,PlaceFlightActivity.class));
                finish();

                break;
            case R.id.toEd:
                RegPrefManager.getInstance(this).setPlace("To");
                startActivity(new Intent(FlightActivity.this,PlaceFlightActivity.class));
                finish();
                break;
            case R.id.travellerEd:
                flightBottomFragment=new FlightBottomFragment();
                flightBottomFragment.show(getSupportFragmentManager(),flightBottomFragment.getTag());
                break;
            case R.id.classEd:
                FlightBottomFragment flightBottomFragment1=new FlightBottomFragment();
                flightBottomFragment1.show(getSupportFragmentManager(),flightBottomFragment1.getTag());
                break;
            case R.id.flightBtn:
                startActivity(new Intent(FlightActivity.this,FlightListActivity.class));
                finish();
                break;
            case R.id.onewayRB:
                deppEd.setVisibility(View.VISIBLE);
                depRel.setVisibility(View.GONE);
                arrRel.setVisibility(View.GONE);
                break;
            case R.id.roundtrip:
                deppEd.setVisibility(View.GONE);
                depRel.setVisibility(View.VISIBLE);
                arrRel.setVisibility(View.VISIBLE);
                break;
            case R.id.deppEd:
                showFlightArrivalDatePicker();
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();


                fromEd.setText(RegPrefManager.getInstance(this).getFromPlace());

                toEd.setText(RegPrefManager.getInstance(this).getToPlace());
        String audltno= RegPrefManager.getInstance(FlightActivity.this).getAdultNo();
        int value=0;
        if(audltno!=null){
            int aud=Integer.parseInt(audltno);
            int childno=Integer.parseInt(RegPrefManager.getInstance(FlightActivity.this).getChildNo());
            int infactno=Integer.parseInt(RegPrefManager.getInstance(FlightActivity.this).getInfantNo());
            value=aud+childno+infactno;

            travellerEd.setText(""+value);
        }

        classEd.setText(RegPrefManager.getInstance(this).getClassName());

    }

    @Override
    protected void onPause() {
        super.onPause();
        String audltno= RegPrefManager.getInstance(FlightActivity.this).getAdultNo();
        int value=0;
        if(audltno!=null){
            int aud=Integer.parseInt(audltno);
            int childno=Integer.parseInt(RegPrefManager.getInstance(FlightActivity.this).getChildNo());
            int infactno=Integer.parseInt(RegPrefManager.getInstance(FlightActivity.this).getInfantNo());
            value=aud+childno+infactno;

            travellerEd.setText(""+value);
        }

        classEd.setText(RegPrefManager.getInstance(this).getClassName());
    }

    @Override
    protected void onStart(){
        super.onStart();

    }


    @Override
    public void onDataChange() {
        flightBottomFragment.dismiss();
        String audltno= RegPrefManager.getInstance(FlightActivity.this).getAdultNo();
        int value=0;
        if(audltno!=null){
            int aud=Integer.parseInt(audltno);
            int childno=Integer.parseInt(RegPrefManager.getInstance(FlightActivity.this).getChildNo());
            int infactno=Integer.parseInt(RegPrefManager.getInstance(FlightActivity.this).getInfantNo());
            value=aud+childno+infactno;

            travellerEd.setText(""+value);
        }

        classEd.setText(RegPrefManager.getInstance(this).getClassName());
    }
}
