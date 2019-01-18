package ratna.genie1.user.genie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

public class DemoSliderActivity extends AppCompatActivity implements
        ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {

    Toolbar toolbar;
    private SliderLayout mDemoSlider;
    private SliderLayout mDemoSlider1;
    private SliderLayout mDemoSlider2;
    int[] image={ R.drawable.contest_banner};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_slider);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mDemoSlider = findViewById(R.id.slider);
        mDemoSlider1 = findViewById(R.id.slider1);
        mDemoSlider2 = findViewById(R.id.slider2);

        setImagePager();
        setImagePager1();
        setImagePager2();
    }


    private void setImagePager() {
        for(int i=0;i<image.length;i++){
            TextSliderView textSliderView = new TextSliderView(DemoSliderActivity.this);
            // initialize a SliderLayout
            textSliderView.image(image[i])
                    .description("Events")
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
//.description("Tourism")
            //add your extra information
            //textSliderView.bundle(new Bundle());
            //textSliderView.getBundle().putString("extra","Tourism");

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    private void setImagePager1() {
        for(int i=0;i<image.length;i++){
            TextSliderView textSliderView = new TextSliderView(DemoSliderActivity.this);
            // initialize a SliderLayout
            textSliderView.image(image[i])
                    .description("Events")
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
//.description("Tourism")
            //add your extra information
            //textSliderView.bundle(new Bundle());
            //textSliderView.getBundle().putString("extra","Tourism");

            mDemoSlider1.addSlider(textSliderView);
        }

        mDemoSlider1.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider1.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider1.setDuration(5000);
        mDemoSlider1.addOnPageChangeListener(this);

    }


    private void setImagePager2() {
        for(int i=0;i<image.length;i++){
            TextSliderView textSliderView = new TextSliderView(DemoSliderActivity.this);
            // initialize a SliderLayout
            textSliderView.image(image[i])
                    .description("Events")
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
//.description("Tourism")
            //add your extra information
            //textSliderView.bundle(new Bundle());
            //textSliderView.getBundle().putString("extra","Tourism");

            mDemoSlider2.addSlider(textSliderView);
        }

        mDemoSlider2.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider2.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider2.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider2.setDuration(5000);
        mDemoSlider2.addOnPageChangeListener(this);

    }




    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
