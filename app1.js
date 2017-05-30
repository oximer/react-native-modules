/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';

import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
  NativeModules,
  DeviceEventEmitter
} from 'react-native';

const MyNativeModule = NativeModules.MyNativeModule;

export default class app1 extends Component {
  constructor(props) {
    super(props);
    this.state = { counter: 0 };
    DeviceEventEmitter.addListener('ListingApp2', payload => {
      this.setState({ counterApp2: payload.message });
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          App1
        </Text>
        <TouchableHighlight
          onPress={() => {
            const counter = this.state.counter + 1;
            this.setState({ counter });
            MyNativeModule.setMessageToApp2(
              `${counter}`,
              () => {
                console.log('enviado com sucesso');
              },
              () => {
                console.error('enviado com erro');
              }
            );
          }}
        >
          <Text style={{ color: 'black' }}>Press me: {this.state.counter}</Text>
        </TouchableHighlight>
        <Text style={{ color: 'black', alignSelf: 'flex-end' }}>
          {`O contador do App2 está em ${this.state.counterApp2}`}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF'
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
    color: 'black'
  }
});

AppRegistry.registerComponent('app1', () => app1);
