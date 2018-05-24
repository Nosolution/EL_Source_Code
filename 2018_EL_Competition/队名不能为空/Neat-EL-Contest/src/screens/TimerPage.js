import React, { Component } from 'react';
import {Container, Content, Footer} from 'native-base';
import {
    View,
    Text,
    Animated,
    Easing,
    StyleSheet,
    ImageBackground
} from 'react-native';
import Timer from "../Timer";


class TimerPage extends Component{
    constructor(props){
        super(props);
        this.state = {};
    }

    render(){
        return (
            <Container style={{flexDirection : 'column'}}>
                <Content scrollEnabled={false}>
                    <ImageBackground  style={{}} source={require('../../lib/images/background_2.png')}>
                <Timer/>
                    </ImageBackground>
                </Content>
            </Container>
        );
    }
}

export default TimerPage;
