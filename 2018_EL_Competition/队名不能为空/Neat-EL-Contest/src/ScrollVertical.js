import React, {Component} from 'react'
import {
    Text,
    View,
    Animated,
    Easing,
    StyleSheet,
} from 'react-native'

export default class ScrollVertical extends Component {
    static defaultProps = {
        enableAnimation: true,
    };

    constructor(props) {
        super(props);
        let translateValue= new Animated.ValueXY({x: 0, y: 0});
        translateValue.addListener(({x,y})=>{
           // log('value',x,y)
        });
        this.state = {
            translateValue: translateValue,
            scrollHeight: this.props.scrollHeight || 32,
            kb_content: [],
            kb_tempValue: 0,
            kb_contentOffsetY: 0,
            delay: this.props.delay || 500,
            duration: this.props.duration || 500,
            enableAnimation: true,
        }
    }

    render() {
        return (
            <View style={[styles.kbContainer, {height: this.state.scrollHeight}, this.props.kbContainer]}>
                {
                    this.state.kb_content.length !== 0 ?
                        <Animated.View
                            style={[
                                {flexDirection: 'column'},
                                {
                                    transform: [
                                        {translateY: this.state.translateValue.y}
                                    ]
                                }
                            ]}>
                            {this.state.kb_content.map(this._createKbItem.bind(this))}
                        </Animated.View> : null
                }
            </View>
        )
    }

    componentWillReceiveProps(nextProps) {
      //  Log('componentWillReceiveProps', nextProps);
        this.setState({
                enableAnimation: nextProps.enableAnimation ? true:false
            }, () => {
                this.startAnimation();
            }
        )
    }

    componentDidMount() {
        let content = this.props.data || [];
        if (content.length !== 0) {
            let h = (content.length + 1) * this.state.scrollHeight;
            this.setState({
                kb_content: content.concat(content[0]),
                kb_contentOffsetY: h
            });
            this.startAnimation();
        }
    }


    _createKbItem(kbItem, index) {
        return (
            <View key={index}
                  style={[{justifyContent: 'center', height: this.state.scrollHeight}, this.props.scrollStyle]}>
                <Text style={[styles.kb_text_c, this.props.textStyle]}>{kbItem.content}</Text>
            </View>
        )
    }

    startAnimation = () => {
        if (this.state.enableAnimation) {
            if(!this.animation){
                this.animation = setTimeout(() => {
                    this.animation=null;
                    this._startAnimation();
                }, this.state.delay);
            }

        }

    };

    componentWillUnmount() {
        if (this.animation) {
            clearTimeout(this.animation);
        }
        if(this.state.translateValue){
            this.state.translateValue.removeAllListeners();
        }
    }

    _startAnimation = () => {
        this.state.kb_tempValue -= this.state.scrollHeight;
        if (this.props.onChange) {
            let index = Math.abs(this.state.kb_tempValue) / (this.state.scrollHeight);
            this.props.onChange(index<this.state.kb_content.length-1?index:0);
        }
        Animated.sequence([
            Animated.timing(
                this.state.translateValue,
                {
                    isInteraction: false,
                    toValue: {x: 0, y: this.state.kb_tempValue},
                    duration: this.state.duration,
                    easing: Easing.linear
                }
            ),
        ])
            .start(() => {
                if (this.state.kb_tempValue - this.state.scrollHeight === -this.state.kb_contentOffsetY) {
                    this.state.translateValue.setValue({x: 0, y: 0});
                    this.state.kb_tempValue = 0;
                }
                this.startAnimation();



            })
    }
}

const styles = StyleSheet.create({
    kbContainer: {
        backgroundColor: 'transparent',
        overflow: 'hidden'
    },
    kb_text_c: {
        fontSize: 18,
        color: 'white',
    }
});