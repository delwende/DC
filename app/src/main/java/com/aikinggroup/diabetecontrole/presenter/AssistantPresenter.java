

package com.aikinggroup.diabetecontrole.presenter;


import com.aikinggroup.diabetecontrole.fragment.AssistantFragment;

public class AssistantPresenter {
    private AssistantFragment fragment;

    public AssistantPresenter(AssistantFragment assistantFragment) {
        this.fragment = assistantFragment;
    }

    public void userAskedAddReading() {
        fragment.addReading();
    }


    public void userSupportAsked() {
        fragment.openSupportDialog();
    }
}
