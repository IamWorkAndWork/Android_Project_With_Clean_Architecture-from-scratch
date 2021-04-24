package app.project.ui.state

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import app.project.ui.databinding.ItemMessageBinding
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MessageItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var binding: ItemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(context), this, true)

    @TextProp
    fun setHeartMessage(hearthMessage: CharSequence) {
        binding.txvHeartMessage.text = hearthMessage
    }

    @TextProp
    fun setInformation(information: CharSequence) {
        binding.txvInformation.text = information
    }

    @TextProp
    fun setMyMessage(myMessage: CharSequence) {
        binding.txvMyMessage.text = myMessage
    }

}