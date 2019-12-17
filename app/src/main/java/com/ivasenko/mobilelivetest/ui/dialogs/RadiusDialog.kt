package com.ivasenko.mobilelivetest.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ivasenko.mobilelivetest.R
import com.ivasenko.mobilelivetest.databinding.DialogRadiusBinding

class RadiusDialog : DialogFragment() {
    companion object {
        const val TAG = "RadiusDialog"
        private const val EXTRA_RADIUS = "radius"

        fun newInstance(radius: Int): RadiusDialog {
            val fragment = RadiusDialog()
            fragment.arguments = bundleOf(EXTRA_RADIUS to radius)
            return fragment
        }
    }

    private var binding: DialogRadiusBinding? = null
    private var listener: RadiusDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? RadiusDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.bind(View.inflate(context, R.layout.dialog_radius, null))
        val builder = AlertDialog.Builder(context)
        binding?.let {
            builder.setView(it.root)
            builder.setTitle(R.string.new_radius_title)
            binding?.value = arguments?.getInt(EXTRA_RADIUS).toString()
        }
        builder.setPositiveButton(R.string.ok) { _, _ ->
            val value = binding?.radiusView?.text.toString().toInt()
            listener?.onNewRadius(value)
        }
        return builder.create()
    }
}