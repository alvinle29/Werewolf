package com.xamk.werewolf

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.xamk.werewolf.model.PlayerItem


class PlayerDialogFragment
    : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // continue here, add below code in here.
        return activity?.let {
            // Custom layout for dialog
            val customView = LayoutInflater.from(context).inflate(R.layout.player_dialog, null)

            // Build dialog
            val builder = AlertDialog.Builder(it)
            builder.setView(customView)
            builder.setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.dialog_ok) { dialog, id ->
                    // create a ShoppingList item
                    val name = customView.findViewById<EditText>(R.id.etName).text.toString()
                    val item = PlayerItem(0, name)
                    mListener.onDialogPositiveClick(item)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.dialog_cancel) { dialog, id ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // Use this instance of the interface to deliver action events
    private lateinit var mListener: AddDialogListener

    /* The activity that creates an instance of this dialog fragment must
      * implement this interface in order to receive event callbacks.
      * Each method passes the DialogFragment in case the host needs to query it. */
    interface AddDialogListener {
        fun onDialogPositiveClick(item: PlayerItem)
    }

    // Override the Fragment.onAttach() method to instantiate the AddDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddDialogListener so we can send events to the host
            mListener = context as AddDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement AddDialogListener"))
        }
    }
}