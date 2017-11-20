package com.greenkeycompany.exam.main.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.greenkeycompany.exam.main.FragmentType;
import com.greenkeycompany.exam.main.activity.presenter.IMainPresenter;
import com.greenkeycompany.exam.main.fragment.ruledescription.view.RuleDescriptionFragment;
import com.greenkeycompany.exam.main.fragment.ruledetail.view.RuleDetailFragment;
import com.greenkeycompany.exam.main.fragment.wordcardresult.view.WordCardResultFragment;
import com.greenkeycompany.exam.main.fragment.wordcardtraining.view.WordCardTrainingFragment;
import com.greenkeycompany.exam.purchase.PurchaseActivity;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.app.RateDialog;
import com.greenkeycompany.exam.main.TrainingType;
import com.greenkeycompany.exam.main.activity.presenter.MainPresenter;
import com.greenkeycompany.exam.app.App;
import com.greenkeycompany.exam.app.PremiumUtil;
import com.greenkeycompany.exam.main.fragment.mainmenu.view.MainMenuFragment;
import com.greenkeycompany.exam.main.fragment.chapterdetail.view.ChapterDetailFragment;
import com.greenkeycompany.exam.main.fragment.trainingmenu.view.TrainingMenuFragment;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpActivity<IMainView, IMainPresenter>
        implements IMainView {

    private boolean interstitialAdClosed;
    private InterstitialAd interstitialAd;

    private ActivityCheckout checkout = Checkout.forActivity(this, App.get().getBilling());

    @NonNull
    @Override
    public IMainPresenter createPresenter() {
        return new MainPresenter();
    }

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_back_button_icon);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_advertisement_id));
        if ( ! PremiumUtil.isPremiumUser()) {
            interstitialAd.loadAd(new AdRequest.Builder().build());
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    interstitialAdClosed = true;
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        }

        checkout.start();
        checkout.loadInventory(Inventory.Request.create().loadAllPurchases(), new Inventory.Callback() {
            @Override
            public void onLoaded(@Nonnull Inventory.Products products) {
                final Inventory.Product product = products.get(ProductTypes.IN_APP);
                if ( ! product.supported) return;

                boolean isPurchased = product.isPurchased(PremiumUtil.PREMIUM_USER_SKU);

                MainActivity.this.setActionBarPremiumButtonVisibility( ! isPurchased);
                PremiumUtil.setPremiumUser(isPurchased);
            }
        });

        RateDialog rateDialog = new RateDialog(this);
        if (rateDialog.isShouldShow()) {
            rateDialog.show();
        }

        presenter.requestToSetMainMenuFragment();
    }

    private static final int PURCHASE_PREMIUM_REQUEST_CODE = 199;

    @BindView(R.id.action_bar_premium_icon) View purchasePremiumView;
    @OnClick(R.id.action_bar_premium_icon)
    public void onPurchasePremiumClick() {
        showPurchasePremiumActivity();
    }

    @Override
    public void showPurchasePremiumActivity() {
        startActivityForResult(new Intent(this, PurchaseActivity.class), PURCHASE_PREMIUM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PURCHASE_PREMIUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    purchasePremiumView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (interstitialAdClosed) {
            interstitialAdClosed = false;
            presenter.onInterstitialAdConsumed();
        }
    }

    @Override
    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            presenter.onInterstitialAdConsumed();
        }
    }

    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setActionBarHomeButtonVisibility(boolean visible) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
    }

    @Override
    public void setActionBarPremiumButtonVisibility(boolean visible) {
        purchasePremiumView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void requestToSetMainMenuFragment() {
        presenter.requestToSetMainMenuFragment();
    }

    @Override
    public void requestToSetChapterDetailFragment(int chapterId) {
        presenter.requestToSetChapterDetailFragment(chapterId);
    }

    @Override
    public void requestToSetRuleDetailFragment(int ruleId) {
        presenter.requestToSetRuleDetailFragment(ruleId);
    }

    @Override
    public void requestToSetRuleDescriptionFragment(int ruleId) {
        presenter.requestToSetRuleDescriptionFragment(ruleId);
    }

    @Override
    public void requestToSetTrainingMenuFragment(int ruleId) {
        presenter.requestToSetTrainingMenuFragment(ruleId);
    }

    @Override
    public void requestToSetWordCardTrainingFragment(@NonNull TrainingType trainingType, int id) {
        presenter.requestToSetWordCardTrainingFragment(trainingType, id);
    }

    @Override
    public void requestToSetWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        presenter.requestToSetWordCardResultFragment(trainingType, resultId, wrongAnswerWordCardIds);
    }

    @Override
    public void setMainFragment() {
        setFragment(MainMenuFragment.newInstance(), FragmentType.MAIN);
    }

    @Override
    public void setChapterDetailFragment(int chapterId) {
        setFragment(ChapterDetailFragment.newInstance(chapterId), FragmentType.CHAPTER);
    }

    @Override
    public void setRuleDetailFragment(int ruleId) {
        setFragment(RuleDetailFragment.newInstance(ruleId), FragmentType.RULE_DETAIL);
    }

    @Override
    public void setRuleDescriptionFragment(int ruleId) {
        setFragment(RuleDescriptionFragment.newInstance(ruleId), FragmentType.RULE_DESCRIPTION);
    }

    @Override
    public void setTrainingMenuFragment(int ruleId) {
        setFragment(TrainingMenuFragment.newInstance(ruleId), FragmentType.TRAINING_MENU);
    }

    @Override
    public void setWordCardTrainingFragment(@NonNull TrainingType trainingType, int id) {
        setFragment(WordCardTrainingFragment.newInstance(trainingType, id), TrainingType.getReferenceTrainingFragmentType(trainingType));
    }

    @Override
    public void setWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds) {
        setFragment(WordCardResultFragment.newInstance(trainingType, resultId, wrongAnswerWordCardIds), TrainingType.getReferenceResultFragmentType(trainingType));
    }

    public void setFragment(Fragment fragment, FragmentType fragmentType) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                addToBackStack(fragmentType.name()).
                commit();
    }

    @Override
    public void onResultFragmentRestartClick(@NonNull TrainingType trainingType) {
        presenter.onResultFragmentRestartClick(trainingType);
    }

    @Override
    public void backStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void backStack(FragmentType fragmentType) {
        getSupportFragmentManager().popBackStack(fragmentType.name(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        checkout.stop();
        super.onDestroy();
    }
}
