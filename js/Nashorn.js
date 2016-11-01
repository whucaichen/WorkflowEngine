//http://docs.Oracle.com/javase/8/docs/technotes/guides/scripting/nashorn/toc.html

//查看包类型
print(java);
print(java.lang);
print(typeof java.lang);
print(typeof java.lang.System);
print(typeof java.lang.System == 'function');
print(_.dir);


//创建Java对象
var HashMap = Java.type("java.util.HashMap");
var mapDef = new HashMap();
var map100 = new HashMap(100);
print(Java.type("java.util.Map").Entry);
print('内部类：' + Java.type("java.util.Map$Entry")); //存取内部类


//JavaString测试
var StringCls = Java.type("java.lang.String");
var str = new StringCls("Hello");
str = str.toUpperCase();
print('Upper: ' + str);


//测试输出
Java.type("java.lang.System").out.println(10);
Java.type("java.lang.System").out["println(double)"](10.92929);


//日期
var Date = Java.type('java.util.Date');
var date = new Date();
print('year:' + date.year);
date.year += 1000;
print('new year:' + date.year);


//字符串处理
print("   hehe".trimLeft());            // hehe
print("hehe    ".trimRight() + "he");   // hehehe


//简单函数的定义
function sqr(x) {
    x * x;
    print(sqr(3));    // 9
}


//把不同对象的属性绑定
var o1 = {age: 15};
var o2 = {name: 'bar'};
Object.bindProperties(o1, o2);   //绑定o2到o1上
print('对象的o1属性：' + o1.name + ', ' + o1.age);    //对象的o1属性：bar, 15
print('对象的o2属性：' + o2.name + ', ' + o2.age);    //对象的o2属性：bar, undefined
o1.name = 'BAM';
print('对象的o2属性：' + o2.name);    //BAM


//load命令
load('http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js');
var odds = _.filter([1, 2, 3, 4, 5, 6], function (num) {
    return num % 2 == 1;
});
print('underscore结果：' + odds);  // 1, 3, 5

//Lamda表达式
var map = Array.prototype.map;
var names = ["john", "jerry", "bob"];
//调用匿名函数，输入参数是names，匿名函数自动遍历names的内容name，并计算其长度
var a = map.call(names, function (name) {
    return name.length()
});
print('Lamda表达式：' + a);


//Java数组
var IntArray = Java.type("int[]");
var iarr = new IntArray(10);
iarr[1] = 5;
iarr[2] = 3;
iarr[3] = iarr[1] + iarr[2];
print('5 + 3 = ' + iarr[3]);
print('iarr.length= ' + iarr.length);
print(iarr[0]);
for (var i in iarr) print(i);  //取得索引下标
//for each(var i in iarr)
//print(i); //取得值


//try...catch用法
try {
    iarr[10] = 5;
}
catch (e) {
    print('try...catch...用法：' + e.message);
    print(e.lineNumber)
    print(e.columnNumber)
    print(e.fileName)
}


//Java.to实现javascript到java数组类型的转换
var jsVals = ["a", "bc", "de"];
var JString = Java.type("java.lang.String[]");
var jVals = Java.to(jsVals, JString);


//数据类型转换
var ival = 10;
print(ival.class);
ival = Number(ival);
print(ival.class);


//Java集合使用
var ArrayList = Java.type("java.util.ArrayList");
var list = new ArrayList();
list.add("zhang");
list.add("wang");
for (var i in list)
    print(i);


//HashMap
print();
print('HashMap demo');
var HashMap = Java.type("java.util.HashMap");
var hm = new HashMap();
hm.put('zhangsan', 133992);
hm.put('lisi', {name: 'martin', sex: 'male'});
for (key in hm.keySet()) print('key: ' + key);
for (val in hm.values()) print('val: ' + val + ' ' + typeof val);


//存取类及其实例成员
print('pi:' + Java.type("java.lang.Math").PI);
print('time:' + Java.type("java.lang.System").currentTimeMillis())
print('ok');


//继承和扩展线程类
var Run = Java.type("java.lang.Runnable");
var MyRun = Java.extend(Run, {
    run: function () {
        print("I am running in separate thread");
    }
});
//构造
var Thread = Java.type("java.lang.Thread");
var th = new Thread(new MyRun());
th.run();


var th1 = new Thread(new MyRun());
th1.run();


//super调用父类方法
var SuperRunner = Java.type('java.lang.Thread');
var Runner = Java.extend(SuperRunner);
var runner = new Runner()
{
    run: function () {
        Java.super(runner).run();
        print('on my run');
    }
}
runner.run();


//用Javascript定义一个类Dog
function Dog(name) {
    this.name = name;
    this.bark = function () {
        return "Hello, " + this.name;
    }
}


var dog = new Dog("martin");
print("dog bark function: " + dog.bark());


//如何继承Object类
//from: https://wiki.openjdk.java.net/display/Nashorn/Nashorn+extensions
/*没模仿成功
 var Object = Java.type("java.lang.Object");
 var Dog = Java.extend(Object);
 var dog = new Dog() {
 bark : function(name) {
 return "hello, " + name;
 }
 }
 print('狗叫：' + dog.bark('martin'));
 */


//Packages用法
var Vector = Packages.java.util.Vector;
// but short-cuts defined for important package prefixes like
//    Packages.java, Packages.javax, Packages.com
//    Packages.edu, Packages.javafx, Packages.org
var JFrame = javax.swing.JFrame;  // javax == Packages.javax
var List = java.util.List;        // java == Packages.java


//exit(1);  //告诉引擎代码执行到这里
//quit(1);


//导入范围---集中一次导入--JavaImporter和with用法
var imports = new JavaImporter(java.io, java.lang);
with (imports) {
    var file = new File(__FILE__);  //查找我在哪里？
    System.out.println('哪里: ' + file.getAbsolutePath());   //内容比较古怪
    // /path/to/my/script.js
}


var CollectionsAndFiles = new JavaImporter(
    java.util,
    java.io,
    java.nio);
with (CollectionsAndFiles) {
    var files = new LinkedHashSet();
    files.add(new File("Plop"));
    files.add(new File("Foo"));
    files.add(new File("w00t.js"));
}


//__LINE__显示当前代码行数，用于调试比较好
print(__FILE__, __LINE__, __DIR__);


print('JavaFX application');
var Button = javafx.scene.control.Button;
var StackPane = javafx.scene.layout.StackPane;
var Scene = javafx.scene.Scene;


/*只能看到最后的JavaFX应用
 function start(primaryStage) {
 primaryStage.title = "Hello World!";
 var button = new Button();
 button.text = "Say 'Hello World'";
 button.onAction = function() print("Hello World!");
 var root = new StackPane();
 root.children.add(button);
 primaryStage.scene = new Scene(root, 300, 250);
 primaryStage.show();
 }
 */


//另一个app--简化版
//但如果前面窗口注释去掉的话，下面的代码未执行
/*
 $STAGE.title = "Hello World!";
 var button = new Button();
 button.text = "Say 'Hello World 2'";
 button.onAction = function() print("Hello World1!");
 var root = new StackPane();
 root.children.add(button);
 $STAGE.scene = new Scene(root, 300, 250);
 $STAGE.show();
 */


//为什么窗口代码只执行最后的内容
load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");


$STAGE.title = "Hello World!";
var button = new Button();
button.text = "Say 'Hello World 3'";
button.onAction = function () {
    print("Hello World!");
}
var root = new StackPane();
root.children.add(button);
$STAGE.scene = new Scene(root, 300, 250);
$STAGE.show();


//复杂些的javafx应用---一个动画演示
load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var WIDTH = 500;
var HEIGHT = 600;
var animation;

function setup(primaryStage) {
    var root = new Group();
    primaryStage.resizable = false;
    var scene = new Scene(root, WIDTH, HEIGHT);
    scene.title = "Colourful Circles";
    primaryStage.scene = scene;

    // create first list of circles
    var layer1 = new Group();
    for (var i = 0; i < 15; i++) {
        var circle = new Circle(200, Color.web("white", 0.05));
        circle.strokeType = StrokeType.OUTSIDE;
        circle.stroke = Color.web("white", 0.2);
        circle.strokeWidth = 4;
        layer1.children.add(circle);
    }

    // create second list of circles
    var layer2 = new Group();
    for (var i = 0; i < 20; i++) {
        var circle = new Circle(70, Color.web("white", 0.05));
        circle.strokeType = StrokeType.OUTSIDE;
        circle.stroke = Color.web("white", 0.1);
        circle.strokeWidth = 2;
        layer2.children.add(circle);
    }

    // create third list of circles
    var layer3 = new Group();
    for (var i = 0; i < 10; i++) {
        var circle = new Circle(150, Color.web("white", 0.05));
        circle.strokeType = StrokeType.OUTSIDE;
        circle.stroke = Color.web("white", 0.16);
        circle.strokeWidth = 4;
        layer3.children.add(circle);
    }

    // set a blur effect on each layer
    layer1.effect = new BoxBlur(30, 30, 3);
    layer2.effect = new BoxBlur(2, 2, 2);
    layer3.effect = new BoxBlur(10, 10, 3);

    // create a rectangle size of window with colored gradient
    var colors = new Rectangle(WIDTH, HEIGHT,
        new LinearGradient(0, 1, 1, 0, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#f8bd55")),
            new Stop(0.14, Color.web("#c0fe56")),
            new Stop(0.28, Color.web("#5dfbc1")),
            new Stop(0.43, Color.web("#64c2f8")),
            new Stop(0.57, Color.web("#be4af7")),
            new Stop(0.71, Color.web("#ed5fc2")),
            new Stop(0.85, Color.web("#ef504c")),
            new Stop(1, Color.web("#f2660f"))));
    colors.blendMode = BlendMode.OVERLAY;

    // create main content
    var group = new Group(new Rectangle(WIDTH, HEIGHT, Color.BLACK),
        layer1,
        layer2,
        layer3,
        colors);
    var clip = new Rectangle(WIDTH, HEIGHT);
    clip.smooth = false;
    group.clip = clip;
    root.children.add(group);

    // create list of all circles
    var allCircles = new java.util.ArrayList();
    allCircles.addAll(layer1.children);
    allCircles.addAll(layer2.children);
    allCircles.addAll(layer3.children);

    // Create a animation to randomly move every circle in allCircles
    animation = new Timeline();
    for (var circle in allCircles) {
        animation.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, // set start position at 0s
                new KeyValue(circle.translateXProperty(), Math.random() * WIDTH),
                new KeyValue(circle.translateYProperty(), Math.random() * HEIGHT)),
            new KeyFrame(new Duration(20000), // set end position at 20s
                new KeyValue(circle.translateXProperty(), Math.random() * WIDTH),
                new KeyValue(circle.translateYProperty(), Math.random() * HEIGHT))
        );
    }
    animation.autoReverse = true;
    animation.cycleCount = Animation.INDEFINITE;
}

function stop() {
    animation.stop();
}

function play() {
    animation.play();
}

function start(primaryStage) {
    setup(primaryStage);
    primaryStage.show();
    play();
}


//JavaFX应用案例
//http://justmy2bits.com/2013/09/08/javafx-with-nashorn-canvas-example/


//编程参考：
//http://www.infoq.com/cn/news/2013/08/everything-about-java-8
//https://wiki.openjdk.java.net/display/Nashorn/Nashorn+Documentation
//https://wiki.openjdk.java.net/display/Nashorn/Main
//https://blogs.oracle.com/nashorn/
//http://winterbe.com/posts/2014/04/05/java8-nashorn-tutorial/
//http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html
//https://wiki.openjdk.java.net/display/Nashorn/Nashorn+extensions