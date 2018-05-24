import React,{Component} from 'react';
import {Container, Content, Text, View} from "native-base";
import {DeviceEventEmitter, Platform, ToastAndroid} from "react-native";
import PostItem from '../postItem';

export default class MyPosts extends Component{
    constructor(props){
        super(props);
        this.state={
            posts:[],
        };
    }
    componentDidMount(){
        this.onFlush();
    }
    onFlush(){
        fetch(`${localURL}/posts?type=&author=${userId}`,{
            method:'GET',
            credentials:'include',
        })
            .then((response) => {
                if (response.ok){
                    response.json().then((response) => {
                        this.setState({
                            posts:response.posts,
                        })
                    });
                } else {
                    response.json().then((json) => (Platform.OS === 'android'?ToastAndroid.show(json.error, ToastAndroid.SHORT):alert(json.error)));
                }
            })
            .catch((error) => {
                Platform.OS === 'android'? ToastAndroid.show(error.message, ToastAndroid.SHORT) : alert(error.message)
            })
    }
    onDelete(id){
        fetch(`${localURL}/posts/${id}/remove/`,{
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
                    response.json().then((json) => ( Platform.OS === 'android'? ToastAndroid.show(json.error, ToastAndroid.SHORT) : alert(json.error)));
                }
            })
            .catch((error) => {
                Platform.OS === 'android'? ToastAndroid.show(error.message, ToastAndroid.SHORT) : alert(error.message)
            });
    }
    render(){
        return(
            <Container>
                <Content>
                    {Object.keys(this.state.posts).length !== 0 ? (
                        <View>
                            {
                                this.state.posts.map((item, i) => <PostItem first last key={i} detail={item} onDelete={() => this.onDelete(item._id)}/>)
                            }
                            </View>
                    ) : (
                        <View>
                            <View style={[{flex: 1,backgroundColor:'transparent'}]}>
                            </View>
                            <View style={[{flex: 1, alignItems: 'center',backgroundColor:'transparent'}]}>
                                <Text>你还没有推文</Text>
                            </View>
                        </View>
                    )
                    }
                </Content>
            </Container>
        );
    }
}
