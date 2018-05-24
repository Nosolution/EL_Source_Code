import React, { Component } from 'react';
import {Button, Container, Content, Text, View,} from 'native-base';
import {DeviceEventEmitter, ImageBackground} from 'react-native';
import {StackNavigator, TabBarBottom, TabNavigator} from 'react-navigation';
import History from '../History';
import Statistics from './Statistics';
import LoginPage from "./LoginPage";
import RegisterPage from "./registerPage";


const Tab = TabNavigator(
    {
        History:{
            screen:History,
            navigationOptions :{
                tabBarLabel:'List',
                tabBarIcon : ({focused}) => (
                    <Text style={{color: focused?'white':'#3d2fff', fontSize:18}}>列表</Text>
                )
            },
        },
        Statistics: {
            screen:Statistics,
            navigationOptions: {
                tabBarLabel:'Statistics',
                tabBarIcon: ({focused}) => (
                    <Text style={{color: focused?'white':'#3d2fff', fontSize:18}}>统计</Text>
                ),
            },
        }
    },
{
    tabBarComponent: TabBarBottom,
    tabBarPosition: 'top',
    lazy: false,
    swipeEnabled: true,
    initialRouteName: 'History',
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
        Tab: {
            screen: Tab,
        },
    },
    {
        initialRouteName: 'Tab',
        headerMode: 'none',
    }
);
class HistoryPage extends Component{
    constructor(props){
        super(props);
        this.state = {};
    }
    d1 = null;d2 = null;d3 = null;
    componentDidMount(){
        this.d1 = DeviceEventEmitter.addListener('login',() => {
            this.forceUpdate();
        });
        this.d2 = DeviceEventEmitter.addListener('logout',() => {
            this.forceUpdate();
        });
        this.d3 = DeviceEventEmitter.addListener('jumpToLogin',() => {
            this.props.navigation.navigate('SocialPage')
        })
    }
    componentWillUnmount(){
        this.d1.remove();this.d2.remove();this.d3.remove();
    }
    render(){
        return (
            <Nav/>
        );
    }
}

export default HistoryPage;
