package lat.pam.hellotoast


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {

    private val model: NameViewModel by viewModels()
    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mShowCount = findViewById<TextView>(R.id.show_count)
        val buttonCountUp = findViewById<Button>(R.id.button_count)
        val buttonToast = findViewById<Button>(R.id.button_toast)
        val buttonSwitchPage = findViewById<Button>(R.id.button_switchpage)
        val buttonBrowser = findViewById<Button>(R.id.button_browser)

        // Restore the count if there is a saved instance state
        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt("count", 0)
        }

        mShowCount.text = mCount.toString()

        val nameObserver = Observer<Int> { newName ->
            mShowCount.text = newName.toString()
        }

        model.currentName.observe(this, nameObserver)

        buttonCountUp.setOnClickListener {
            mCount++
            model.currentName.value = mCount
        }

        buttonToast.setOnClickListener {
            val tulisan = mShowCount.text.toString()
            val toast = Toast.makeText(this, "Angka yang dimunculkan $tulisan", Toast.LENGTH_SHORT)
            toast.show()
        }

        buttonSwitchPage.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java).apply {
                putExtra("key_nama", "M khaerul ilman")
                putExtra("key_umur", 21)
            }
            startActivity(intent)
        }

        buttonBrowser.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", mCount)
    }
}
