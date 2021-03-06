package org.dasta.quizzes.lawquiz;

import android.widget.*;
import org.dasta.quizzes.lawquiz.datautils.DataStore;
import org.dasta.quizzes.lawquiz.datautils.Question;
import org.dasta.quizzes.lawquiz.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import org.w3c.dom.Text;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = false;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    /**
     * The current question
     */
    private Question q;

    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // quiz related initialization
        DataStore.randomize();
        DataStore.initialize(contentView.getContext(), "grile.txt", "rasp.txt");

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.gotoapp_button).setOnTouchListener(mDelayHideTouchListener);
        findViewById(R.id.check_box_controls).setVisibility(LinearLayout.INVISIBLE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    /***
     * So far so good
     * */
    public void showQuiz(View view){

        loadNextQuestion(view);

        LinearLayout checkBoxLayout = (LinearLayout)findViewById(R.id.check_box_controls);
        checkBoxLayout.bringToFront();
        checkBoxLayout.setVisibility(LinearLayout.VISIBLE);

        findViewById(R.id.fullscreen_content_controls).setVisibility(TextView.INVISIBLE);
        view.refreshDrawableState();

    }

    private void loadNextQuestion(View view){
        q = DataStore.nextQuestion();

        TextView questionText = (TextView)findViewById(R.id.textViewQuestion);
        questionText.setText(q.getQuestion());

        CheckBox checkA = (CheckBox)findViewById(R.id.checkBoxA);
        checkA.setText("A) " + q.getAnswerTexts().get(0));
        checkA.setChecked(false);

        CheckBox checkB = (CheckBox)findViewById(R.id.checkBoxB);
        checkB.setText("B) " + q.getAnswerTexts().get(1));
        checkB.setChecked(false);

        CheckBox checkC = (CheckBox)findViewById(R.id.checkBoxC);
        checkC.setText("C) " + q.getAnswerTexts().get(2));
        checkC.setChecked(false);

        LinearLayout checkBoxLayout = (LinearLayout)findViewById(R.id.check_box_controls);
        View ansView = checkBoxLayout.getChildAt(checkBoxLayout.getChildCount() - 1);

        ansView.setVisibility(TextView.INVISIBLE);
    }


    private int[] getScores(View view){
        int[] ans = new int[3];

        CheckBox checkA = (CheckBox)findViewById(R.id.checkBoxA);
        ans[0] = checkA.isChecked() ? 1 : 0;
        CheckBox checkB = (CheckBox)findViewById(R.id.checkBoxB);
        ans[1] = checkB.isChecked() ? 1 : 0;
        CheckBox checkC = (CheckBox)findViewById(R.id.checkBoxC);
        ans[2] = checkC.isChecked() ? 1 : 0;

        return ans;
    }

    private void showScore(View view){
        if (!answered) {
            DataStore.answerQuestion(q, getScores(view));
            answered = true;
        }
        TextView scoreValue = (TextView) findViewById(R.id.textViewScoreValue);
        scoreValue.setText(DataStore.getScoreString());
    }

    public void nextQuestion(View view){
        showScore(view);
        loadNextQuestion(view);
        setEnabledCheckboxes(view, true);
        answered = false;
    }

    private void setEnabledCheckboxes(View view, boolean enabled){
        CheckBox checkA = (CheckBox)findViewById(R.id.checkBoxA);
        checkA.setEnabled(enabled);
        CheckBox checkB = (CheckBox)findViewById(R.id.checkBoxB);
        checkB.setEnabled(enabled);
        CheckBox checkC = (CheckBox)findViewById(R.id.checkBoxC);
        checkC.setEnabled(enabled);
    }

    public void showAnswers(View view){
        showScore(view);
        LinearLayout checkBoxLayout = (LinearLayout)findViewById(R.id.check_box_controls);
        View ansView = checkBoxLayout.getChildAt(checkBoxLayout.getChildCount() - 1);
        ((CheckBox)ansView.findViewById(R.id.checkBoxAA)).setChecked(q.getCorrectAnswers()[0] == 1);
        ((CheckBox)ansView.findViewById(R.id.checkBoxAB)).setChecked(q.getCorrectAnswers()[1] == 1);
        ((CheckBox)ansView.findViewById(R.id.checkBoxAC)).setChecked(q.getCorrectAnswers()[2] == 1);
        ansView.setVisibility(TextView.VISIBLE);
        setEnabledCheckboxes(view, false);
    }
}
