package com.automationexercise.media;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class ScreenRecordManager {
    public final static String RECORDINGS_PATH = PropertyReader.getProperty("video.folder");
    private static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();

    /**
     * Starts screen recording.
     */
    public static void startRecording() {
        if (PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                //Ensure the recording directory exists
                File recordingDir = new File(RECORDINGS_PATH);
                if (!recordingDir.exists()) {
                    recordingDir.mkdirs();
                }

                //Configure the recorder to use the custom directory and file name

                if(PropertyReader.getProperty("executionType").equalsIgnoreCase("local")){
                    recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));

                    //start recording
                    recorder.get().start();
                    LogsManager.info("Screen recording started.");
                }

            } catch (Exception e) {
                LogsManager.error("Error starting screen recording: " + e.getMessage());
            }
        }
    }
    /**
     * Stop Recording and return the video as an InputStream.
     */
    public static void stopRecording(String testMethodName) {
        try {
            if (recorder.get() != null) {
                //Stop the recording and get the video file
                String videoFilePath = String.valueOf(recorder.get().stopAndSave(testMethodName));
                File videoFile = new File(videoFilePath);

                //Log the file path for debugging
                LogsManager.info("Screen recording stopped. Video saved at: " + videoFilePath);

                //Convert the video to .mp4 format
                File mp4File = encodeRecording(videoFile);
                LogsManager.info("Screen recording encoded to MP4 format: " + mp4File.getPath());
            }
        } catch (Exception e) {
            LogsManager.error("Error stopping screen recording: " + e.getMessage());
        }
    }

    /**
     * Converts a video file to .mp4 format.
     *
     * @param sourceFile the input video file.
     * @return the converted .mp4 file.
     */
    private static File encodeRecording(File sourceFile) {
        File targettFile = new File(sourceFile.getParent(), sourceFile.getName().replace(".avi", ".mp4"));
        try {
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac"); //AAC audio codec

            VideoAttributes video = new VideoAttributes();
            video.setCodec("libx264"); //H.264 video codec

            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setAudioAttributes(audio);
            encodingAttributes.setVideoAttributes(video);

            //Encode the video
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(sourceFile), targettFile, encodingAttributes);

            //Delete the original .avi file after conversion
            if (targettFile.exists()) {
                sourceFile.delete();
                LogsManager.info("Original recording file deleted: " + sourceFile.getPath());
            }
        } catch (Exception e) {
            LogsManager.error("Error encoding screen recording: " + e.getMessage());
        }
        return targettFile;
    }
}

