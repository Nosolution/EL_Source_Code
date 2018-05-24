import React,{Component} from 'react';
import {Button, Col, Container, Content, Grid, Input, Item, Label, Picker, Text, View} from "native-base";
import {DeviceEventEmitter, Modal, StyleSheet} from 'react-native';

class Tiktok extends Component{
    constructor(props){
        super(props);
        this.state = {
            timee: this.props.timee,
            min:Tiktok.formatter(this.props.timee / 60),
            sec:Tiktok.formatter(this.props.timee % 60),
            isPause:false,
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
        countDown && clearInterval(countDown);
        this.d1.remove();
    }
    tiktok(){
        global.countDown = setInterval(() => {
            if (this.state.timee > 0){
                let time = this.state.timee;
                this.setState({
                    timee:this.state.timee - 1,
                    min:Tiktok.formatter((time - 1) / 60),
                    sec:Tiktok.formatter((time - 1) % 60),
                });
            }else{
                this.endCount();
            }}, 50
        );
    }
    onButtonClick=() => {
        this.state.isPause?this.tiktok():clearInterval(countDown);
        this.setState({isPause:!this.state.isPause});
    };
    onAbandonButtonClick=() => {
        clearInterval(countDown);
        this.setState({isPause:true});
        this.props.onAbandon();
    };
    render(){
        return(
            <View style={[{alignItems:'center', alignContent:'center', alignSelf:'center'}]}>
                <Text textAlign='center' style={[{fontSize: 30, color:'#ff4000'}]}>{this.state.min} : {this.state.sec}</Text>
                <Button block onPress={() => this.onButtonClick()}>
                    <Text>{this.state.isPause? '继续':'暂停'}</Text>
                </Button>
                <Button block onPress={() => this.onAbandonButtonClick()}>
                    <Text>放弃</Text>
                </Button>
            </View>
        );
    }
}

export default class YTimer extends Component{
    constructor(props){
        super(props);
        this.state = {
            targetTime: "0",
            title:null,
            isReady: true,
            selected: undefined,
            modalTransparent: true,
            modalVisible:false,
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
    timesUp(){
        let newHistory = new FormData();
        newHistory.append("title",this.state.title === null?"专注就是妙":this.state.title);
        newHistory.append("length",this.state.targetTime);
        DeviceEventEmitter.emit('flush', newHistory);
        this.setState({isReady: true, modalVisible:false, selected:"0"});
    };
    uncommonTimesUp(){
        this.setState({isReady: true, modalVisible:false, selected:"0"});
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
            <Container style={[{flexDirection:'column',justifyContent:'center'}]}>
                {this.state.isReady ? (
                    <View style={[{flexDirection:'column',justifyContent:'center'}]}>
                        <Label>专注项目名称：</Label>
                        <Item rounded style={[{backgroundColor:'#fff'}]}>
                            <Input multiline={false}
                                   placeholder={'标题'}
                                   onChangeText={(text) => this.setState({title:text})}
                            />
                        </Item>
                        <Label>专注时长：</Label>
                        <Picker
                            mode="dropdown"
                            placeholder="Select one"
                            selectedValue={this.state.selected}
                            onValueChange={this.onValueChange.bind(this)}
                            style={[{width: 200,alignSelf:'center'}]}
                        >
                            <Picker.Item disabled label='Select one' value='0'/>
                            <Picker.Item label="1" value='1' />
                            <Picker.Item label="5" value='5' />
                            <Picker.Item label="15" value='15' />
                            <Picker.Item label="30" value='30' />
                            <Picker.Item label="60" value='60' />
                        </Picker>
                        <Button style={[{borderRadius:40,alignSelf:'center',marginTop:20}]} disabled={this.state.targetTime === '0'} onPress={() => this.startTiming()}>
                            <Text>开始专注</Text>
                        </Button>
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
                        <Tiktok timee={parseInt(this.state.targetTime) * 60} onAbandon={() => this.setModalVisible(true)}/>
                    </View>
                )}
            </Container>
        );
    }
}

const styles = StyleSheet.create({
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
