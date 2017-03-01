package com.pavithbuddhima.solvemath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelMenu extends AppCompatActivity implements  View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);
    }

    public void onClick(View v) {

        switch (v.getId()){

            case(R.id.btnNovice):
                Intent objNewGame = new Intent(this,LevelMenu.class);
                startActivity(objNewGame);
                break;
            case(R.id.btnEasy):   ;
                break;
            case(R.id.btnMedium):   ;
                break;
            case(R.id.btnGuru):   ;
                break;

        }

    }

}
