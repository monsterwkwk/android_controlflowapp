package id.co.iconpln.controlflowapp.bottomSheetDialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.co.iconpln.controlflowapp.R
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_content_main.*
class BottomSheetActivity : AppCompatActivity(), View.OnClickListener, BottomSheetFragment.ItemClickListener {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        setupActionBar()
        setButtonClickListener()
        setupBottomSheetBehavior()
    }

    private fun setButtonClickListener() {
        btnBottomSheet.setOnClickListener(this)
        btnBottomSheetDialog.setOnClickListener(this)
        btnBottomDialogFragment.setOnClickListener(this)
        btnProcessPayment.setOnClickListener(this)
    }

    private fun setupBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(view: View, slideOffset: Float) {

                }

                override fun onStateChanged(view: View, newState: Int) {
                    when(newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            btnBottomSheet.text = "Expand Bottom Sheet"
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {}
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            btnBottomSheet.text = "Close Bottom Sheet"
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                        BottomSheetBehavior.STATE_HIDDEN -> {}
                        BottomSheetBehavior.STATE_SETTLING -> {}
                    }
                }

            }
        )
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbarBottomSheet)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btnBottomSheet -> {
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    btnBottomSheet.text = "Close Bottom Sheet"
                }
            }
            R.id.btnBottomSheetDialog -> {
                val dialogView = layoutInflater
                    .inflate(R.layout.fragment_bottom_sheet, null)
                val bottomSheetDialog = BottomSheetDialog(this)
                bottomSheetDialog.setContentView(dialogView)
                bottomSheetDialog.show()
            }
            R.id.btnBottomDialogFragment -> {
                val bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
            R.id.btnProcessPayment -> {
                tvBottomActivity.setText("Button Payment Clicked")
            }
        }
    }

    override fun onItemClick(text: String) {
        tvBottomActivity.text = text
    }

}
