package com.greenkeycompany.exam.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.greenkeycompany.exam.repository.model.WordCardSet;

import org.solovyev.android.checkout.Billing;

import java.io.IOException;

import javax.annotation.Nonnull;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tert0 on 11.10.2017.
 */

public class App extends Application {

    @Nonnull
    private final Billing billing = new Billing(this, new Billing.DefaultConfiguration() {
        @Nonnull
        @Override
        public String getPublicKey() {
            return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhKfa9+kXOPadOWgoSD2aYY28fHj2zS3f8z8LsbxVXRtSvwsJwXRJlt/9236EzelTDitthAKFqFV4maf8seSCjgvdrk8CnglM07rcKi+/lrqaG6jvPOb/34n3tBaemVlyMMIGC5QZU7PR3ICDZJUHshNf9AT3GXakF/cHKTcgfVYH6v3LzjpHrk0zfffu1P8ytoXlytVxAKCSbqcfCZauB0tMVAoMnbSfaMECK7NWXQA31DvO66p4iQfYKcIukOSfL9uuHziXF8fhRTT9+mlFH4tpdsHKNOfAn0gEHWTBIO3wNWJGNDgTn/cyX5WkFJyr5sH2m1DJIpSNuIZZRvk7sQIDAQAB";
        }
    });

    @Nonnull
    public Billing getBilling() {
        return billing;
    }

    private static App instance;
    private static void init(App instance) {App.instance = instance; }
    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.init(this);
        Realm.init(this);
        PremiumUtil.init(this);
        MobileAds.initialize(this, getString(R.string.application_admob_id));

        RealmConfiguration config = new RealmConfiguration.Builder().initialData(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    AssetManager assetManager = getResources().getAssets();

                    realm.createAllFromJson(Chapter.class, assetManager.open("chapters.json"));
                    realm.createAllFromJson(Rule.class, assetManager.open("rules.json"));
                    realm.createAllFromJson(RulePoint.class, assetManager.open("rule_points.json"));
                    realm.createAllFromJson(WordCardSet.class, assetManager.open("word_card_sets.json"));
                    realm.createAllFromJson(WordCard.class, assetManager.open("word_cards.json"));
                } catch (IOException e) {
                    Log.d("RealmConfiguration", e.getMessage());
                }
            }
        }).deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(config);
    }
}
