package com.sensirion.smartgadget.utils.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;

public abstract class ParentListFragment extends ListFragment {

    private final String TAG = this.getClass().getSimpleName();

    @Nullable
    protected Activity mActivity = null;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (!Activity.class.isInstance(context)) {
            throw new RuntimeException("Must attach an Activity");
        }
        mActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mActivity == null) {
            Log.w(TAG, "setUserVisibleHint() -> was called before activity was set up!");
        } else if (isVisibleToUser) {
            Log.i(TAG, "setUserVisibleHint() -> Visible to user -> triggering onResume()");
            onResume();
        } else {
            Log.i(TAG, "setUserVisibleHint() -> Invisible to user -> triggering onPause()");
            onPause();
        }
    }

    /**
     * Gets the parent activity of the device.
     *
     * @return {@link android.app.Activity} of the fragment.
     */
    @Nullable
    public Activity getParent() {
        final Activity activity = super.getActivity();
        if (activity == null) {
            return mActivity;
        }
        mActivity = activity;
        return mActivity;
    }
}