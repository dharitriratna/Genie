package ratna.genie1.user.genie;

import android.content.Intent;

interface DataObjectHolder {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
