package com.greenkeycompany.exam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.greenkeycompany.exam.app.App;

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

    private PremiumUtil premiumUtil = App.get().getPremiumUtil();
    private ActivityCheckout checkout = Checkout.forActivity(this, App.get().getBilling());

    private boolean isPurchased;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //@BindView(R.id.premium_purchase_button) Button purchaseButton;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_activity);
        unbinder = ButterKnife.bind(this);
        checkout.start();

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_back_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkout.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        checkout.stop();
        super.onDestroy();
    }

    /*
    @OnClick(R.id.premium_purchase_button)
    public void onPremiumButtonClick() {
        checkout.startPurchaseFlow(ProductTypes.IN_APP, PremiumUtil.PREMIUM_USER_SKU, null, new RequestListener<Purchase>() {
            @Override
            public void onSuccess(@Nonnull Purchase result) {
                isPurchased = true;
                premiumUtil.setPremiumUser(true);
                purchaseButton.setVisibility(View.INVISIBLE);
                Toast.makeText(PurchaseActivity.this, R.string.purchase_success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(int response, @Nonnull Exception e) {
                Toast.makeText(PurchaseActivity.this, R.string.purchase_error, Toast.LENGTH_LONG).show();
            }
        });
    }
    */

    @Override
    public void finish() {
        setResult(isPurchased ? RESULT_OK : RESULT_CANCELED);
        super.finish();
    }
}
