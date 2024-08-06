package app.SourceCode.Fundamental;

import javax.speech.*;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TextToSpeech {
    private static final String VOICES_KEY = "freetts.voices";
    private static final String VOICE_VALUE = "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory";
    private static final String CENTRAL_DATA = "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral";
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(); // Single-thread executor

    public static void voice(String data) {
        executorService.submit(() -> {
            try {
                System.setProperty(VOICES_KEY, VOICE_VALUE);
                Central.registerEngineCentral(CENTRAL_DATA);
                Synthesizer sy = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
                sy.allocate();
                sy.resume();
                sy.speakPlainText(data, null);
                sy.addEngineListener(new EngineListener() {
                    public void engineAllocated(EngineEvent event) {
                    }

                    @Override
                    public void engineAllocatingResources(EngineEvent engineEvent) {
                    }

                    public void engineDeallocated(EngineEvent event) {
                    }

                    @Override
                    public void engineDeallocatingResources(EngineEvent engineEvent) {
                    }

                    @Override
                    public void engineError(EngineErrorEvent engineErrorEvent) {
                    }

                    @Override
                    public void enginePaused(EngineEvent engineEvent) {
                    }

                    @Override
                    public void engineResumed(EngineEvent engineEvent) {
                    }

                    public void engineError(EngineEvent event) {
                    }

                    public void speakableEnded(EngineEvent event) throws EngineException {
                        // Deallocate the Synthesizer when speaking is done
                        sy.deallocate();
                    }
                });

                while (sy.getEngineModeDesc().toString().equals("Speaking")) {
                    Thread.sleep(100);
                }
            } catch (EngineException | AudioException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
