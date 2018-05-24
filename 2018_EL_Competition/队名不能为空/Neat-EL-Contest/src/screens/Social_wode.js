import React, { Component } from 'react';
import {DeviceEventEmitter, Platform, StyleSheet,TouchableNativeFeedback,ToastAndroid,ImageBackground,Image} from "react-native";
import {Body, Button, Text, Container, Content, Header, Icon, Left, List, ListItem, Right, Separator, View} from "native-base";


export default class Social_wode extends Component{
    constructor(props){
        super(props);
        this.state={};
    }
    logout(){
        fetch(`${localURL}/signout/`,{
            method:'GET',
            credentials:'include',
        })
            .then((response) => {
                if (response.ok) {
                    isLogin = false;
                    userId = null;
                    nickname = null;
                    DeviceEventEmitter.emit('logout');
                }else {
                    response.json().then((json) => (Platform.OS === 'android'?ToastAndroid.show(json.error, ToastAndroid.SHORT) : alert(json.error)));
                }
            })
            .catch((error) => {
                Platform.OS === 'android'?ToastAndroid.show(error.message, ToastAndroid.SHORT) : alert(error.message)
            });
    }
    render(){
        return(
            <Container>
                <ImageBackground  style={{}} source={require('../../lib/images/head_portrait.png')}>
                <Header style={{height:240, opacity: 0,flexDirection:'column'}}>
                </Header>
                    <View style={{alignSelf:'center'}}>
                        <Text style={{fontSize:30,color:'#222'}}>{nickname}</Text>
                    </View>
                </ImageBackground>
                <Content scrollEnabled={false}>
                    <List>
                        <ImageBackground style={{}} source={require('../../lib/images/background_3.png')}>
                        <Container>
                            <Content scrollEnabled={false}>
                        <ListItem icon first last
                                  style={styles.listitem}
                                  background={TouchableNativeFeedback.SelectableBackground()}
                                  onPress={() => {
                                      this.props.navigation.navigate('MyPosts');
                                  }}
                        >
                            <Left>
                                <Icon name='person' style={{color:'#3d2fff'}}/>
                            </Left>
                            <Body>
                            <Text>我的推文</Text>
                            </Body>
                            <Right>
                                {Platform.OS === "ios" && <Icon active name="arrow-forward" />}
                            </Right>
                        </ListItem>
                        <ListItem icon first last
                                  style={styles.listitem}
                                  background={TouchableNativeFeedback.SelectableBackground()}
                                  onPress={() => {
                                      this.props.navigation.navigate('MyFavorite');
                                  }}
                        >
                            <Left>
                                <Icon name='star' style={{color:'#fc4'}}/>
                            </Left>
                            <Body>
                            <Text>我的收藏</Text>
                            </Body>
                            <Right>
                                {Platform.OS === "ios" && <Icon active name="arrow-forward" />}
                            </Right>
                        </ListItem>
                        <ListItem icon first last style={styles.listitem}>
                            <Left>
                                <Icon name='cog' style={{color:'#333'}}/>
                            </Left>
                            <Body>
                            <Text>设置</Text>
                            </Body>
                            <Right>
                                {Platform.OS === "ios" && <Icon active name="arrow-forward" />}
                            </Right>
                        </ListItem>

                        <Separator style={styles.separator}/>

                        <ListItem button first last
                                  style={styles.listitem}
                                  background={TouchableNativeFeedback.SelectableBackground()}
                                  onPress={() => {
                                      this.logout();
                                  }}
                        >
                            <Body>
                            <Text style={{color:'red',textAlign:'center'}}>退出登录</Text>
                            </Body>
                        </ListItem>
                            </Content>
                        </Container>
                        </ImageBackground>
                    </List>
                </Content>
            </Container>
        )
    }
}

const styles = StyleSheet.create({
    listitem:{
        backgroundColor:'white',
    },
    separator:{
        backgroundColor:'transparent',
    }
});
