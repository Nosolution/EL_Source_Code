import React,{Component} from 'react';
import {Button, Col, Container, Content, Grid, Input, Item, Label, Picker, Text, View} from "native-base";
import {DeviceEventEmitter, Modal, StyleSheet,Platform,AppState,Vibration} from 'react-native';
import SoundPlay from './SoundPlay';
import RadioModal from 'react-native-radio-master';
import BackgroundTimer from 'react-native-background-timer';
import ScrollVertical from './ScrollVertical';


const dataArray = [
    {
        title: '专注大法好',
    },
    {
        title: '我爱专注',
    },
    {
        title: '专注爱我',
    }
];

class Tiktok extends SoundPlay{
    constructor(props){
        super(props);
        let isPlaySound = this.props.isPlaySound;
        this.state = {
            timee: this.props.timee,
            min:Tiktok.formatter(this.props.timee / 60),
            sec:Tiktok.formatter(this.props.timee % 60),
            isPause:false,
            choose : this.props.choose,
            isPlaySound:isPlaySound
        };
    }
    d1 = null;
    static formatter(timeLeft){
        if (timeLeft < 10){
            return '0'+parseInt(timeLeft);
        }else {
            return parseInt(timeLeft);
        }
    }
    endCount(){
        DeviceEventEmitter.emit('timesUp')
    }
    componentWillMount(){
        this.tiktok();
    }
    componentDidMount(){
        this.d1 = DeviceEventEmitter.addListener('abandonCanceled',() => {this.tiktok();this.setState({isPause:false});});
    }
    componentWillUnmount(){
         countDown &&(Platform.OS === 'android'? BackgroundTimer.clearInterval(countDown) : clearInterval(countDown));
        this.d1.remove();
        this.stopSoundLooped();
    }
    tiktok(){
        this.state.isPlaySound ? this.playSoundLoop() :  {};
        if(Platform.OS === 'android'){
        global.countDown = BackgroundTimer.setInterval(() => {
            if (this.state.timee > 0){
                let time = this.state.timee;
                this.setState({
                    timee:this.state.timee - 1,
                    min:Tiktok.formatter(Math.trunc((time - 1) / 60)),
                    sec:Tiktok.formatter((time - 1) % 60),
                });
            }else{
                this.endCount();
            }}, 1000
        );}else{
            if(AppState.currentState === 'active'){
                BackgroundTimer.stopBackgroundTimer();
            global.countDown = setInterval(() => {
                if (this.state.timee > 0){
                    let time = this.state.timee;
                    this.setState({
                        timee:this.state.timee - 1,
                        min:Tiktok.formatter(Math.trunc((time - 1) / 60)),
                        sec:Tiktok.formatter((time - 1) % 60),
                    });
                }else{
                    this.endCount();
                }}, 1000
            );
        }else{
                 BackgroundTimer.runBackgroundTimer(() => {
                    if (this.state.timee > 0){
                        let time = this.state.timee;
                        this.setState({
                            timee:this.state.timee - 3,
                            min:Tiktok.formatter(Math.trunc((time - 3) / 60)),
                            sec:Tiktok.formatter((time - 3) % 60),
                        });
                    }else{
                        this.endCount();
                    }}, 3000
                );
    }}}
    stopCountDown(){
       Platform.OS === 'android' ? BackgroundTimer.clearInterval(countDown) : clearInterval(countDown);
        this.stopSoundLooped();
    }
    onButtonClick=() => {
        this.state.isPause?this.tiktok():this.stopCountDown();
        this.setState({isPause:!this.state.isPause});
    };
    onAbandonButtonClick=() => {
        Platform.OS === 'android' ? BackgroundTimer.clearInterval(countDown) : clearInterval(countDown);
        this.setState({isPause:true});
        this.props.onAbandon();
    };
    render(){
        let array = [{ content: '' }];
        if (dataArray && dataArray.length > 0) {
            array = [];
            for (let item of dataArray) {
                array.push({ content: item.title});
            }
        }
        return(
            <View>
                <View>
                    <View style={[{marginTop:70,justifyContent: 'center',alignItems:'center', alignContent:'center', alignSelf:'center'}]}>
                <Text  style={[{fontSize:77, color:'#3d2fff',fontWeight:'bold'}]}>{this.state.min} : {this.state.sec}</Text>
                    </View>
                    <View style={[{marginTop:50, alignSelf:'center',flexDirection : 'row'}]}>
                <Button style={{borderRadius:100, width:100, height:100}} onPress={() => this.onButtonClick()}>
                    <Text style={[{fontSize : 30}]}>{this.state.isPause? '继续':'暂停'}</Text>
                </Button>
                <Button style={{borderRadius:100,marginTop:50,width:100, height:100}} onPress={() => this.onAbandonButtonClick()}>
                    <Text  style={[{fontSize : 30}]}>放弃</Text>
                </Button>
                    </View>
            </View>
            <View>
    <ScrollVertical
        onChange={(index => {
            this.index = index;
        })}
        enableAnimation={true}
        data={array}
        delay={2500}
        duration={1000}
        scrollHeight={34}
        scrollStyle={{alignItems: 'center' }}
        />
            </View>
            </View>
        );
    }
}



export default class Timer extends SoundPlay {
    constructor(props){
        super(props);
        this.state = {
            targetTime: "0",
            title:null,
            isReady: true,
            selected: null,
            modalTransparent: true,
            modalVisible:false,
            isPlaySound:false,
        };
    }
    l2 = null;
    componentDidMount(){
        this.l2 = DeviceEventEmitter.addListener('timesUp',() => {this.timesUp();});
    }
    componentWillUnmount(){
        this.l2.remove();
    }
    setModalVisible(visible) {
        this.setState({modalVisible: visible});
    }
    startTiming(){
        this.setState({
            isReady: false,
        });
    };
    selected(item){
        if(item.initItem === '不播放白噪音'){
            this.setState({isPlaySound : false});
        }else if(item.initItem === '林间之声'){
            this.setState({
                isPlaySound : true,
                choose : 0
            });
        }else if(item.initItem === '海洋波潮'){
            this.setState({
                isPlaySound : true,
                choose : 1
            });
        }else if(item.initItem === '淅淅夏雨'){
            this.setState({
                isPlaySound : true,
                choose : 2
            });
        }else if(item.initItem === '山谷蛙鸣'){
            this.setState({
                isPlaySound : true,
                choose : 3
            });
        }else if(item.initItem === '潺潺泉涌'){
            this.setState({
                isPlaySound : true,
                choose : 4
            });
        }
    }
    timesUp(){
        let newHistory = new FormData();
        newHistory.append("title",this.state.title === null?"专注就是妙":this.state.title);
        newHistory.append("length",this.state.targetTime);
        DeviceEventEmitter.emit('flush', newHistory);
        this.setState({isReady: true, modalVisible:false, selected:"0",isPlaySound:false,targetTime : '0'});
        Vibration.vibrate();
    };
    uncommonTimesUp(){
        this.setState({isReady: true, modalVisible:false,isPlaySound : false, selected:"0",targetTime : '0'});
        this.state.isPlaySound ? this.stopSoundLooped() :  {};
    }
    onValueChange(value: string){
        this.setState({
            selected: value,
            targetTime: parseInt(value),
        });
    }
    render(){
        let modalBackgroundStyle = {
            backgroundColor: this.state.modalTransparent ? 'rgba(0, 0, 0, 0.5)' : '#ffffff',
        };
        let innerContainerTransparentStyle = this.state.modalTransparent ?
            {backgroundColor: '#fff', padding: 20}
            : null;
        return(
            <Container style={[{flexDirection:'column',marginTop:10}]}>
                {this.state.isReady ? (
                    <View style={[{flexDirection:'column'}]}>
                        <Label style={{color:'white'}}>专注项目：</Label>
                        <Item rounded style={[{backgroundColor:'#fff'}]}>
                            <Input multiline={false}
                                   placeholder={'专注就是妙'}
                                   style={[{color : '#000000'}]}
                                   onChangeText={(text) => this.setState({title:text})}
                            />
                        </Item>
                        <Label style={{color:'white'}}>专注时长：</Label>
                        <Picker
                            mode="dropdown"
                            style={[{color : 'white'}]}
                            placeholder="选择时长"
                            selectedValue={this.state.selected}
                            onValueChange={this.onValueChange.bind(this)}
                        >
                            <Picker.Item disabled label='选择时长' value='0'/>
                            <Picker.Item label="1" value='1' />
                            <Picker.Item label="5" value='5' />
                            <Picker.Item label="15" value='15' />
                            <Picker.Item label="30" value='30' />
                            <Picker.Item label="60" value='60' />
                        </Picker>
                        <Button block style={[{borderRadius:40,marginTop:8}]} disabled={this.state.targetTime == '0'} onPress={() => this.startTiming()}>
                            <Text>开始专注</Text>
                        </Button>
                        <RadioModal
                            txtColor = {'white'}
                            selectedValue={this.state.initId}
                            onValueChange={(id,item)=>this.selected({initItem : item})}
                        >
                            <Text value='0'>不播放白噪音</Text>
                            <Text value='1'>林间之声</Text>
                            <Text value='2'>海洋波潮</Text>
                            <Text value='3'>淅淅夏雨</Text>
                            <Text value='4'>山谷蛙鸣</Text>
                            <Text value='5'>潺潺泉涌</Text>
                        </RadioModal>
                    </View>
                ) : (
                    <View>
                        <View>
                            <Modal
                                animationType='fade'
                                transparent={this.state.modalTransparent}
                                visible={this.state.modalVisible}
                                onRequestClose={() => {this.setModalVisible(false)}}
                            >
                                <View style={[styles.container, modalBackgroundStyle]}>
                                    <View style={[styles.innerContainer, innerContainerTransparentStyle]}>
                                        <Text>确认放弃此次专注吗？</Text>
                                        <Grid>
                                            <Col style={[styles.gridStyle, {height:50}]}>
                                                <Button transparent full
                                                        onPress={() => this.uncommonTimesUp()}
                                                        style={styles.buttonStyle}
                                                >
                                                    <Text style={[{color:'red'}]}>确认</Text>
                                                </Button>
                                            </Col>
                                            <Col style={[styles.gridStyle, {height:50}]}>
                                                <Button transparent full
                                                        onPress={() => {
                                                            this.setModalVisible(false);
                                                            DeviceEventEmitter.emit('abandonCanceled');
                                                        }}
                                                        style={styles.buttonStyle}
                                                >
                                                    <Text>取消</Text>
                                                </Button>
                                            </Col>
                                        </Grid>
                                    </View>
                                </View>
                            </Modal>
                        </View>
                        <Tiktok
                            isPlaySound = {this.state.isPlaySound}
                            choose = {this.state.choose}
                            timee={parseInt(this.state.targetTime) * 60}
                            onAbandon={() => this.setModalVisible(true)}
                        />
                    </View>
                )}
            </Container>
        );
    }
}
const styles = StyleSheet.create({
    image: {
        flex: 1,
        paddingBottom:50,
    },
    buttonText : {
        color:'#463cff',
        fontWeight: 'bold',
        fontSize:17,
    },
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 20,
    },
    innerContainer: {
        borderRadius: 10,
        height:100,
        alignItems: 'center',
    },
    gridStyle:{
        justifyContent:'center',
        alignItems:'center',
    },
    buttonStyle:{
        alignSelf:'center',
    }
});
