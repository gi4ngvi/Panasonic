package isobar.panasonic.utility;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.monte.media.Format;
import org.monte.media.VideoFormatKeys;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;

public abstract class ScreenRecorder {
    protected static final int VIDEO_FRAME_RATE = 15;
    protected static final int KEY_FRAME_INTERVAL = VIDEO_FRAME_RATE * 30; // Set key frame every 30 seconds
    protected static final int VIDEO_DEPTH_KEY = 16;
    protected static final float VIDEO_QUALITY_KEY = 1.0f;
    protected static final long MAX_RECORDING_TIME = 3000000;

    protected static GraphicsConfiguration gc;
    protected static org.monte.screenrecorder.ScreenRecorder screenRecorder;

    static {
        gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
        try {
            screenRecorder = new org.monte.screenrecorder.ScreenRecorder(gc,
                    null,
                    new Format(VideoFormatKeys.MediaTypeKey, MediaType.FILE, VideoFormatKeys.MimeTypeKey, VideoFormatKeys.MIME_QUICKTIME),
                    new Format(VideoFormatKeys.MediaTypeKey, MediaType.VIDEO, VideoFormatKeys.EncodingKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            VideoFormatKeys.CompressorNameKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            VideoFormatKeys.DepthKey, VIDEO_DEPTH_KEY, VideoFormatKeys.FrameRateKey, Rational.valueOf(VIDEO_FRAME_RATE),
                            VideoFormatKeys.QualityKey, VIDEO_QUALITY_KEY,
                            VideoFormatKeys.KeyFrameIntervalKey, KEY_FRAME_INTERVAL),
                    new Format(VideoFormatKeys.MediaTypeKey, MediaType.VIDEO, VideoFormatKeys.EncodingKey, "black",
                            VideoFormatKeys.FrameRateKey, Rational.valueOf(10)), null, new File(System.getProperty("user.dir")));
            screenRecorder.setMaxRecordingTime(MAX_RECORDING_TIME);

        } catch (Exception ex) {
            throw new IllegalStateException("Failed to initialize Screen Recorder", ex);
        }
    }


    public static String getLastVideo() {
        try {
            List<File> fileList = screenRecorder.getCreatedMovieFiles();
            return fileList.get(fileList.size() - 1).getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void startRecording() throws IOException {
        screenRecorder.start();
    }

    public static void stopRecording() throws IOException {
        screenRecorder.stop();
    }
}