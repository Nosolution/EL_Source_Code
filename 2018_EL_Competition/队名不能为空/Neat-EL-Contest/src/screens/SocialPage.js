import React, { Component } from 'react';
import {Container, Content, Text, } from 'native-base';
import {StackNavigator, TabBarBottom, TabNavigator} from 'react-navigation';
import {DeviceEventEmitter, ImageBackground} from 'react-native';
import LoginPage from './LoginPage';
import Social_wode from './Social_wode';
import Social_quanzi from './Social_quanzi';
import MyPosts from './MyPosts';
import MyFavorite from './Myfavorite';


const Tab = TabNavigator(
    {
        Social_wode:{
            screen : Social_wode,
            navigationOptions :{
                tabBarLabel:'Mine',
                tabBarIcon : ({focused}) => (
                    <Text style={{color: focused?'white':'#3d2fff', fontSize:18}}>我的</Text>
                )
            },
        },
        Social_quanzi: {
            screen: Social_quanzi,
            navigationOptions: {
                tabBarLabel:'Moments',
                tabBarIcon: ({focused}) => (
                    <Text style={{color: focused?'white':'#3d2fff', fontSize:18}}>圈子</Text>
                ),
            },
        },
    },
    {
        tabBarComponent: TabBarBottom,
        tabBarPosition: 'top',
        lazy: false,
        swipeEnabled: true,
        initialRouteName: 'Social_wode',
        tabBarOptions:{
            activeTintColor: 'white',
            inactiveTintColor: '#3d2fff',
            style:{
                backgroundColor: '#1d1150',
            },
        }
    },
);
const Nav = StackNavigator(
    {
        Tab:{
            screen: Tab,
        },
        MyPosts:{
            screen: MyPosts,
        },
        MyFavorite:{
            screen: MyFavorite,
        }
    },
    {
        initialRouteName: 'Tab',
        headerMode: 'none',
    }
);
class SocialPage extends Component{
    constructor(props){
        super(props);
        this.state = {};
    }
    d1 = null;d2 = null;
    componentDidMount(){
        this.d1 = DeviceEventEmitter.addListener('login',() => {
            this.forceUpdate();
        });
        this.d2 = DeviceEventEmitter.addListener('logout',() => {
            this.forceUpdate();
        })
    }
    componentWillUnmount(){
        this.d1.remove();this.d2.remove();
    }
    render(){
        return (
            !isLogin?(
                <Container>
                    <Content>
                        <LoginPage navigation={this.props.navigation}/>
                    </Content>
                </Container>
            ):(
            <Nav/>
            )
        );
    }
}

export default SocialPage;
