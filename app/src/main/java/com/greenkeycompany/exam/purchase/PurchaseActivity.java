package com.greenkeycompany.exam.purchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.app.App;
import com.greenkeycompany.exam.app.PremiumUtil;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.RequestListener;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PurchaseActivity extends AppCompatActivity {

    private ActivityCheckout checkout = Checkout.forActivity(this, App.get().getBilling());

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.purchase_premium_view) View purchasePremiumView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_activity);

        unbinder = ButterKnife.bind(this);
        checkout.start();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.premium_access);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_back_button_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkout.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.purchase_premium_view)
    public void onPurchasePremiumClick() {
        checkout.startPurchaseFlow(ProductTypes.IN_APP, PremiumUtil.PREMIUM_USER_SKU, null, new RequestListener<Purchase>() {
            @Override
            public void onSuccess(@Nonnull Purchase result) {
                PremiumUtil.setPremiumUser(true);
                PurchaseActivity.this.setResult(RESULT_OK);

                purchasePremiumView.setVisibility(View.INVISIBLE);
                Toast.makeText(PurchaseActivity.this, R.string.purchase_success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(int response, @Nonnull Exception e) {
                Toast.makeText(PurchaseActivity.this, R.string.purchase_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        checkout.stop();
        unbinder.unbind();
        super.onDestroy();
    }
}
