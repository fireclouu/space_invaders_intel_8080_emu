package com.fireclouu.intel8080emu;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout; 
import com.fireclouu.intel8080emu.emulator.Inputs;

public class EmulatorActivity extends Activity implements Button.OnTouchListener, Button.OnClickListener {
    Display display;
    HostPlatform platform;

    // Buttons
    private Button mButtonCoin, mButtonP1Start, mButtonP1Left, mButtonP1Right, mButtonP1Fire, mButtonSetPlayer, mButtonLogs;

    private LinearLayout llLogs;
	private LinearLayout llLogs2;
	
    private RelativeLayout rlEmulator;

    private boolean isTestSuite;

    private String romFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_emulation);
        isTestSuite = getIntent().getBooleanExtra(HostUtils.INTENT_FILE_IS_TEST_ROM, false);
        romFileName = getIntent().getStringExtra(HostUtils.INTENT_ROM_FILE_NAME);

        llLogs2 = findViewById(R.id.llLogs2);
        llLogs = findViewById(R.id.llLogs);
        rlEmulator = findViewById(R.id.rlEmulator);

        llLogs.setVisibility(isTestSuite ? View.VISIBLE : View.GONE);
        rlEmulator.setVisibility(!isTestSuite ? View.VISIBLE : View.GONE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (isTestSuite) llLogs.setLayoutParams(params);
		
        init();
        startEmulation();
    }
	
	private void init() {
		requestFullscreen();
        display = findViewById(R.id.mainDisplay);

        mButtonCoin = findViewById(R.id.btn_p1_coin);
        mButtonP1Start = findViewById(R.id.btn_p1_start);
        mButtonP1Left = findViewById(R.id.btn_p1_left);
        mButtonP1Fire = findViewById(R.id.btn_p1_fire);
        mButtonP1Right = findViewById(R.id.btn_p1_right);
        mButtonSetPlayer = findViewById(R.id.btn_change_player);
        mButtonLogs = findViewById(R.id.btn_logs);

        mButtonCoin.setOnTouchListener(this);
        mButtonP1Start.setOnTouchListener(this);
        mButtonP1Left.setOnTouchListener(this);
        mButtonP1Fire.setOnTouchListener(this);
        mButtonP1Right.setOnTouchListener(this);
        mButtonSetPlayer.setOnClickListener(this);
        mButtonLogs.setOnClickListener(this);
    }
	
	private void startEmulation() {
        if (platform == null) {
            platform = new HostPlatform(this, this, display, isTestSuite);
            platform.setRomFileName(romFileName);
        }

        platform.start();
    }

    @Override
    protected void onResume() {
        // platform.setPause(false);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // platform.setPause(true);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        platform.stop();
        platform.releaseResource();
        platform = null;
        super.onDestroy();
    }

    // separate interrupt from platform-specific data
    // move in implementation
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        byte playerPort = platform.getPlayerPort();
        int action = motionEvent.getAction() & MotionEvent.ACTION_MASK;
        boolean hasAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP;
        boolean isDown = action == MotionEvent.ACTION_DOWN;
        int buttonId = view.getId();
        byte key = 0;

        if (buttonId == R.id.btn_p1_coin) key = Inputs.KEY_COIN;
        if (buttonId == R.id.btn_p1_start) key = Inputs.KEY_P1_START;
        if (buttonId == R.id.btn_p1_fire) key = Inputs.KEY_FIRE;
        if (buttonId == R.id.btn_p1_left) key = Inputs.KEY_LEFT;
        if (buttonId == R.id.btn_p1_right) key = Inputs.KEY_RIGHT;

        if (hasAction) platform.sendInput(playerPort, key, isDown);
        return false;
    }

    @Override
    public void onClick(View view) {
        byte playerPort = platform.getPlayerPort();
        int buttonId = view.getId();
        if (buttonId == R.id.btn_change_player) {
            playerPort = playerPort == Inputs.INPUT_PORT_1 ? Inputs.INPUT_PORT_2 : Inputs.INPUT_PORT_1;
            platform.setPlayerPort(playerPort);
            mButtonSetPlayer.setText("P" + playerPort);
        }

        if (buttonId == R.id.btn_logs) {
            boolean isVisible = llLogs.getVisibility() == View.VISIBLE;
            int toggledVisibility = isVisible ? View.GONE : View.VISIBLE;
            llLogs.setVisibility(toggledVisibility);
            isVisible = llLogs.getVisibility() == View.VISIBLE;
            platform.toggleLog(isVisible);	
        }
    }

    private void requestFullscreen() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
