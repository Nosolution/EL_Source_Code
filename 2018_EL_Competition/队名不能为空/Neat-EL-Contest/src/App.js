import React, { Component } from 'react';
import {DeviceEventEmitter} from 'react-native';
import {Icon, Root} from 'native-base';
import {StackNavigator, TabBarBottom, TabNavigator} from 'react-navigation';
import HistoryPage from "./screens/HistoryPage";
import TimerPage from "./screens/TimerPage";
import SocialPage from "./screens/SocialPage";
import RegisterPage from './screens/registerPage';
import LoginPage from "./screens/LoginPage";


const Tab = TabNavigator(
    {
        HistoryPage:{
            screen: HistoryPage,
            navigationOptions:{
                tabBarLabel: 'History',
                tabBarIcon: ({focused}) => (<Icon name = 'paper' style={{color: focused?'white':'#3d2fff'}}/>),
                tabBarOnPress:(obj) => {
                    DeviceEventEmitter.emit('flush');
                    obj.jumpToIndex(obj.scene.index);
                }
            },
        },
        TimerPage:{
            screen: TimerPage,
            navigationOptions:{
                tabBarLabel: 'Timer',
                tabBarIcon: ({focused}) => (<Icon name = 'alarm' style={{color: focused?'white':'#3d2fff'}}/>)
            },
        },
        SocialPage:{
            screen: SocialPage,
            navigationOptions:{
                tabBarLabel: 'Social',
                tabBarIcon: ({focused}) => (<Icon name = 'person' style={{color: focused?'white':'#3d2fff'}}/>),
                tabBarOnPress:(obj) => {
                    DeviceEventEmitter.emit('socialflush');
                    obj.jumpToIndex(obj.scene.index);
                }
            },
        },
    },

    {
        tabBarComponent: TabBarBottom,
        tabBarPosition: 'bottom',
        lazy: false,
        swipeEnabled: false,
        initialRouteName: 'TimerPage',
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
            navigationOptions:{},
        },
        Login:{
            screen: LoginPage,
            navigationOptions:{},
        },
        Register: {
            screen: RegisterPage,
            navigationOptions:{
                headerTitle:'注册',
                headerTitleStyle:{
                    alignSelf:'center',
                }
            },
        }
    },
    {
        initialRouteName: 'Tab',
        headerMode: 'none',
    }
);

export default class App extends Component{

    render(){
        return(
            <Root>
                <Nav/>
            </Root>
        );
    }
}
