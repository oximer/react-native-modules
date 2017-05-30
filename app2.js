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

export default class app2 extends Component {
  constructor(props) {
    super(props);
    this.state = { counter: 0 };
    DeviceEventEmitter.addListener('ListingApp1', payload => {
      this.setState({ counterApp1: payload.message });
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          App2
        </Text>
        <TouchableHighlight
          onPress={() => {
            const counter = this.state.counter + 1;
            this.setState({ counter });
            MyNativeModule.setMessageToApp1(
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
          {`O contador do App2 está em ${this.state.counterApp1}`}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'blue'
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5
  }
});

AppRegistry.registerComponent('app2', () => app2);
