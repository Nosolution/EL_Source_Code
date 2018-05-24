import React, {Component} from 'react';
import {Body, Card, CardItem, Text} from "native-base";
import moment from "moment";

export default class HistoryItem extends Component{
    onDelete(){
        this.props.onDelete();
    }
    render(){
        return(
            <Card>
                <CardItem bordered={true}>
                    <Body>
                    <Text>任务名称：{this.props.detail.title}</Text>
                    <Text>专注时长：{this.props.detail.length} 分钟</Text>
                    <Text>日期：{moment(this.props.detail.date).format('YYYY-MM-DD')}</Text>
                    </Body>
                </CardItem>
                <CardItem footer button onPress={() => this.onDelete()}>
                    <Text style={{color: 'red'}}>删除</Text>
                </CardItem>
            </Card>
        )
    }
}
