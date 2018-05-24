import React, {Component} from 'react';
import {Button, Container, Content, Text, View} from "native-base";
import HistoryItem from './HistoryItem';
import {DeviceEventEmitter, ToastAndroid, Platform, ImageBackground} from 'react-native';


export default class History extends Component{
    constructor(props){
        super(props);
        this.state = {
            historys:[],
        };
    }
    d1 = null;d2 = null;
    componentDidMount(){
        this.d1 = DeviceEventEmitter.addListener('flush',(newHistory) => {
            if (isLogin) {
                if (newHistory) {
                    fetch(`${localURL}/historys/create/`, {
                        method: 'POST',
                        headers: {},
                        body: newHistory,
                        credentials: 'include',
                    })
                        .then((response) => {
                            if (response.ok) {
                                response.json().then((json) => {
                                    Platform.OS === 'android'? ToastAndroid.show(json.message, ToastAndroid.SHORT) : alert(json.message);
                                });
                                this.onFlush();
                            } else if (response.status === 500) {
                                response.json().then((json) => (Platform.OS === 'android' ?ToastAndroid.show(json.error, ToastAndroid.SHORT) : alert(json.error)));
                            }
                        })
                        .catch((error) => {
                            Platform.OS === 'android' ? ToastAndroid.show(error.message, ToastAndroid.SHORT) : alert(error.message);
                        });
                }else{
                    this.onFlush();
                }
            }
        });
        this.d2 = DeviceEventEmitter.addListener("login",() => this.onFlush());
        this.d3 = DeviceEventEmitter.addListener("logout",() => this.forceUpdate());
        this.onFlush();
    }
    componentWillUnmount(){
        this.d1.remove();
        this.d2.remove();
        this.d3.remove();
    };
    onDelete(id){
        fetch(`${localURL}/historys/${id}/remove/`,{
            method:'GET',
            credentials:'include',
        })
            .then((response) => {
                if (response.ok){
                    this.onFlush();
                    response.json().then((json) => {
                        Platform.OS === 'android'? ToastAndroid.show(json.message, ToastAndroid.SHORT) : alert(json.message)
                    })
                } else if (response.status === 500) {
                    response.json().then((json) =>  (Platform.OS === 'android' ?ToastAndroid.show(json.error, ToastAndroid.SHORT) : alert(json.error)));
                }
            })
            .catch((error) => {
                Platform.OS === 'android'?ToastAndroid.show(error.message, ToastAndroid.SHORT) : alert(error.message)
            });
    }
    onFlush(){
        fetch(`${localURL}/historys/get/`,{
            method:'GET',
            credentials:'include',
        })
            .then((response) => {
                if (response.ok){
                    response.json().then((responseHistory) => {
                        this.setState({
                            historys:responseHistory.history,
                        })
                    });
                } else {
                    response.json().then((json) => (Platform.OS === 'android'? ToastAndroid.show(json.error, ToastAndroid.SHORT) : alert(json.error)));
                }
            })
            .catch((error) => {
                Platform.OS === 'android'?ToastAndroid.show(error.message, ToastAndroid.SHORT):alert(error.message)
            })
    }
    render(){
        return(
            <Container>
                <Content>
                    {isLogin? (Object.keys(this.state.historys).length !== 0?(
                        <ImageBackground style={{}} source={require('../lib/images/background_1.png')}>
                        <Container>
                            <Content>
                        <View>
                            {
                                this.state.historys.map((item,i) => <HistoryItem key={i} detail={item} onDelete={() => this.onDelete(item._id)}/>)
                            }
                            </View>
                            </Content>
                        </Container>
                        </ImageBackground>
                        ):(
                        <ImageBackground style={{}} source={require('../lib/images/background_1.png')}>
                            <Container>
                                <Content>
                            <View style={[{flex:1, alignItems:'center'}]}>
                                <Text>你还没有专注历史</Text>
                            </View>
                                </Content>
                            </Container>
                        </ImageBackground>
                            )
                        ):(
                        <ImageBackground style={{}} source={require('../lib/images/background_1.png')}>
                            <Container>
                                <Content>
                                    {isLogin?
                                        <History/>:
                                        <View>
                                            <Text>你还没有登陆</Text>
                                            <Text>不登陆你能变强吗？</Text>
                                            <Button onPress={() => DeviceEventEmitter.emit('jumpToLogin')}>
                                                <Text>去登陆</Text>
                                            </Button>
                                        </View>
                                    }
                                    </Content>
                            </Container>
                            </ImageBackground>
                    )
                }
                </Content>
            </Container>
        );
    }
}
