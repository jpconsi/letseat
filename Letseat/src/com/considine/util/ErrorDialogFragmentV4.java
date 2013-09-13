package com.considine.util;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ErrorDialogFragmentV4 extends DialogFragment {

	// Global field to contain the error dialog
	private Dialog mDialog;

	// Default constructor. Sets the dialog field to null
	public ErrorDialogFragmentV4() {
		super();
		mDialog = null;
	}

	// Set the dialog to display
	public void setDialog(Dialog dialog) {
		mDialog = dialog;
	}

	// Return a Dialog to the DialogFragment.
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return mDialog;
	}

}
