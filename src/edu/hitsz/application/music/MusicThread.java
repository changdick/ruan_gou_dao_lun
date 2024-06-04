package edu.hitsz.application.music;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;


/**
 * MusicThread 类 来自课程提供，
 * 音乐文件通过文件名，在类初始化时读入流，流转成AudioFormat，以及字节数组samples，
 * 线程运行时，samples又转输入流，与AudioFormat用于play()方法播放。
 * @author 220110430 吴梓滔
 * @注释 有些bgm是单次播放，如子弹击中和道具生效音效，那么只需要使用本来的MusicThread类直接播放，如bgm一直玩的话是循环播放，因此要修改
 * 可以在MusicThread类里面引入一个标记，即是否循环的标记，如果标记是要循环，那么就循环播放
 *
 */

public class MusicThread extends Thread {


    //音频文件名
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    private Boolean loop;
    private Boolean myIsInterrupted = false;

    /**
     * musicSwitch 为静态变量，音效开关的选择传到这里，musicSwitch为True的时候，run才会有可执行代码，为False的时候，run就空跑
     * 设置这个静态变量，只需要在这个类里面加一行代码就可以实现音效开关
     */
    private static Boolean musicSwitch;

    public MusicThread(String filename, Boolean loop) {
        //初始化filename
        this.filename = filename;
        this.loop = loop;
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            //用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     Reads the samples from the audio input stream.
     @param stream the audio input stream
     @return an array of bytes representing the audio samples
     */
    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return samples;
    }
    /**
     Plays the audio from the given input stream.
     @param source the input stream containing the audio data
     */
    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        //源数据行SoureDataLine是可以写入数据的数据行
        SourceDataLine dataLine = null;
        //获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dataLine.start();
        try {
            int numBytesRead = 0;
            // 2024/5/9 13:37 实验五 本来用的是!this.isInterrupted()，现在改成自己设置的flag
            while (numBytesRead != -1 && !this.myIsInterrupted) {
                //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead =
                        source.read(buffer, 0, buffer.length);
                //通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();

    }

    @Override
    public void run() {
        if (MusicThread.musicSwitch) {
            InputStream stream;
            do{
//            InputStream stream = new ByteArrayInputStream(samples);
                stream = new ByteArrayInputStream(samples);
                play(stream);
//                System.out.println("BGM还在播放，isInterrupted为"+this.isInterrupted());debug用的

            }while(loop && !myIsInterrupted);

        }

    }

    @Override
    public void interrupt() {
        super.interrupt();
        myIsInterrupted = true;
    }

    public static void setMusicSwitch(Boolean musicSwitch) {
        MusicThread.musicSwitch = musicSwitch;
    }
}


// 2024/5/9 13:34 耗费半个小时处理中断，game里边调用了bgm_musicThread.interrupt()后，默认的interrupted属性没有变成True，但是自己设置一个flag就能够正常完成任务