
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const Home(),
    );
  }
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  static const platform = MethodChannel('floating_button');

  int count = 0;

  @override
  void initState() {
    super.initState();
    
     platform.setMethodCallHandler((methodCall) async {
      if(methodCall.method == 'touch') {
        setState(() {
          count += 1;
        });
      }
      
    });

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Floating Button Demo'),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              "$count",
              textAlign: TextAlign.center,
              style: const TextStyle(fontSize: 50),
            ),
            TextButton(
              child: const Text('Create'),
              onPressed: () {
                platform.invokeMethod('create');
              },
            ),
            TextButton(
              child: const Text('Show'),
              onPressed: () {
                platform.invokeMethod('show');
              },
            ),
            TextButton(
              child: const Text('hide'),
              onPressed: () {
                platform.invokeMethod('hide');
              },
            ),
            TextButton(
              child: const Text('Verify'),
              onPressed: () {
                // ignore: avoid_print
                platform.invokeMethod('isShowing').then((isShowing) => print(isShowing));
              },
            ),
          ],
        ),
      ),
    );
  }
}
