package com.tripaza.tripaza.ui.camera

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tripaza.tripaza.databinding.ActivityCameraBinding
import com.tripaza.tripaza.ml.MLFoodModel
import org.tensorflow.lite.support.image.TensorImage
import java.io.File
import java.util.concurrent.ExecutorService


class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraExecutor: ExecutorService

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    companion object{
        private const val TAG = "CameraActivity"
    }
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        cameraExecutor = Executors.newSingleThreadExecutor()

//        binding.cameraIvCapture.setOnClickListener { takePhoto() }
//        binding.cameraIcSwitchCameraMode.setOnClickListener {
//            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
//            else CameraSelector.DEFAULT_BACK_CAMERA
//            startCamera()
//        }
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                12473
            )
            finish()
            Toast.makeText(this, "Permission is Required", Toast.LENGTH_SHORT).show()
        }
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, 333)
        startDialog()
        

    }
    private fun startDialog() {
        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        myAlertDialog.setTitle("Upload Pictures Option")
        myAlertDialog.setMessage("How do you want to set your picture?")
        myAlertDialog.setPositiveButton("Gallery",
            DialogInterface.OnClickListener { arg0, arg1 ->
                startGallery()
            })
        myAlertDialog.setNegativeButton("Camera",
            DialogInterface.OnClickListener { arg0, arg1 ->
                val intent = Intent( MediaStore.ACTION_IMAGE_CAPTURE )
//                val f = File( Environment.getExternalStorageDirectory(), "temp.jpg")
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
                startActivityForResult(intent, 333)
            })
        myAlertDialog.show()
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
    private val launcherIntentGallery = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImgUri: Uri = result.data?.data as Uri
            try{
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImgUri)
                classifyImage(bitmap)
                binding.ivTakenPhoto.setImageBitmap(bitmap)
            }catch (e: Exception){
                
            }
        }
    }
    
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode== RESULT_OK){
            if (requestCode == 333){
                var image = data?.extras?.get("data") as Bitmap
                val dimension = Math.min(image.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                classifyImage(image)
                binding.ivTakenPhoto.setImageBitmap(image)
            }
        }
        
        super.onActivityResult(requestCode, resultCode, data)
    }
    
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun classifyImage(image: Bitmap){
        val model = MLFoodModel.newInstance(this)
//        val byteBuffer = ByteBuffer.allocateDirect(4*500*500*3)
//        byteBuffer.order(ByteOrder.nativeOrder())
        // Creates inputs for reference.
        val image = TensorImage.fromBitmap(image)

        // Runs model inference and gets result.
        val outputs = model.process(image)
        val detectionResult = outputs.detectionResultList.get(0)
        
        // Gets result from DetectionResult.
        val location = detectionResult.scoreAsFloat;
        val category = detectionResult.locationAsRectF;
        val score = detectionResult.categoryAsString;
        Log.d(TAG, "classifyImage: LOCATION: " + location)
        Log.d(TAG, "classifyImage: CATEGORY: " + category)
        Log.d(TAG, "classifyImage: SCORE: " + score)
        // Releases model resources if no longer used.
        binding.location.text = location.toString()
        binding.category.text = category.toString()
        binding.score.text = score.toString()
        
        
        
        for(d in outputs.detectionResultList){
            val location = d.scoreAsFloat;
            val category = d.locationAsRectF;
            val score = d.categoryAsString;
            Log.d(TAG, "classifyImage: LOCATION: " + location)
            Log.d(TAG, "classifyImage: CATEGORY: " + category)
            Log.d(TAG, "classifyImage: SCORE: " + score)
        }
        
        
        
        model.close()

    }
    
    
    
    /*
    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Failed to take image",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra("picture", photoFile)
                    intent.putExtra(
                        "isBackCamera",
                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    )
//                    setResult(AddStoryActivity.CAMERA_X_RESULT_CODE, intent)
//                    finish()
                }
            }
        )
    }

    private fun createFile(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else application.filesDir

        return File(outputDirectory, "TempFile.jpg")
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraViewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Failed to start camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    */
}