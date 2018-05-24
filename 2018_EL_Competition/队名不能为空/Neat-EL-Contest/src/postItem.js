import React, {Component} from 'react';
import {Body, Button, Card, CardItem, Left, Right, Text} from "native-base";
import moment from "moment";

export default class PostItem extends Component{
    constructor(props){
        super(props);
        this.state = {};
    }
    onDelete(){
        this.props.onDelete();
    }
    render(){
        return(
            <Card style={{flex:0}}>
                <CardItem bordered>
                    <Body>
                    <Text style={{fontSize:20,margin:0}}>{this.props.detail.author.nickname}:</Text>
                    </Body>
                </CardItem>
                <CardItem>
                    <Body>
                    <Text>{`#${this.props.detail.type === 'normal'?'动态':'经验'}#   ${this.props.detail.content}`}</Text>
                    </Body>
                </CardItem>
                <CardItem>
                    <Left>
                        <Text note>{moment(this.props.detail.date).format('YYYY-MM-DD HH:mm')}</Text>
                    </Left>
                    <Right>
                {this.props.detail.author._id === userId ? (
                            <Button transparent textStyle={{color: '#05f'}} onPress={() => this.onDelete()}>
                                <Text>删除</Text>
                            </Button>
                ) : (null)
                }
                    </Right>
                </CardItem>
            </Card>
        )
    }
}
