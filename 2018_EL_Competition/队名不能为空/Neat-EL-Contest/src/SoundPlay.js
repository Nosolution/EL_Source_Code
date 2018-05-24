import React, { Component } from 'react';
import Sound from 'react-native-sound';

class SoundPlay extends Component{

    constructor(props){
        super(props);
        let choose = Math.random() * 5 | 0;
        Sound.setCategory('Playback');
        this.state = {
            choose : choose,
            loopingSound : undefined,
            isBeginPlay : true,
        };
        }
    playSoundLoop(){
        if(this.state.loopingSound){
            return;
        }
        if (this.state.choose === 0 ){const whiteSound = new Sound('blue_and_white_flycatcher.mp3',Sound.MAIN_BUNDLE,(error)=>{
            if(error){
                alert('Sound error');
            }
            whiteSound.setNumberOfLoops(-1);
            whiteSound.play();
        });
            this.setState({
                loopingSound : whiteSound,
            })}
        else if(this.state.choose === 1){const whiteSound = new Sound('gentle_ocean_waves.mp3',Sound.MAIN_BUNDLE,(error)=>{
            if(error){
                alert('Sound error');
            }
            whiteSound.setNumberOfLoops(-1);
            whiteSound.play();
        });
            this.setState({
                loopingSound : whiteSound,
            })}
        else if(this.state.choose === 2){ const whiteSound = new Sound('rain.mp3',Sound.MAIN_BUNDLE,(error)=>{
            if(error){
                alert('Sound error');
            }
            whiteSound.setNumberOfLoops(-1);
            whiteSound.play();
        });
            this.setState({
                loopingSound : whiteSound,
            })}
        else if(this.state.choose === 3){
            const whiteSound = new Sound('small_stream.mp3',Sound.MAIN_BUNDLE,(error)=>{
                if(error){
                    alert('Sound error');
                }
                whiteSound.setNumberOfLoops(-1);
                whiteSound.play();
            });
            this.setState({
                loopingSound : whiteSound,
            })}
        else{ const whiteSound = new Sound('water.mp3',Sound.MAIN_BUNDLE,(error)=>{
            if(error){
                alert('Sound error');
            }
            whiteSound.setNumberOfLoops(-1);
            whiteSound.play();
        });
            this.setState({
                loopingSound : whiteSound,
            })}
    };
    stopSoundLooped(){
        if(!this.state.loopingSound){
            return;
        }
        this.state.loopingSound
            .stop()
            .release();
        this.setState({
            loopingSound : null,
        })
    };
}
module.exports = SoundPlay;
