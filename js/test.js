//http://stackoverflow.com/questions/25379340/nashorn-java-collections-how-to-implement-equals-and-hashcode-in-pure-javascri
load("nashorn:mozilla_compat.js");

var JsImpl = Java.extend(com.chance.util.JsImpl);
function jsImplTest() {
    return new JsImpl(){
        doAny: function() {
            return "doAny";
        }
    }
}
var two = new jsImplTest();
function LogTest3() {
    two.doSth();
}

var SocketHelper = Java.extend(com.chance.SocketHelper);
var s = new java.net.Socket("localhost", 50026);
function LogTest4(){
    var sh = new SocketHelper(s){
        onReceive: function(inetAddress, str){
            print(inetAddress+ " : "+ str);
        },
        onDisconnect: function(inetAddress){
            print("js log: disConn from "+ inetAddress);
        }
    }
    sh.start();
}

//父类
function person() {
    this.hair = 'black';
    this.eye = 'black';
    this.skin = 'yellow';
    this.view = function () {
        return this.hair + ',' + this.eye + ',' + this.skin;
    }
}
//子类
function man() {
    this.feature = ['beard', 'strong'];
}
man.prototype = new person();
var one = new man();
function LogTest() {
    print(one.hair);
    print(one.eye);
    print(one.skin);
    print(one.view());
}

var JSVertex = function (from, cost) {
    this.from = +from;
    this.cost = +cost;
};
JSVertex.prototype = {
    equals: function (other) {
        print("[JSVertex.prototype.equals " + this + "]");
        return this.from === other.from;
    },
    hashCode: function () {
        var hash = java.lang.Integer.hashCode(this.from);
        print("[JSVertex.prototype.hashCode " + this + " : " + hash + "]");
        return hash;
    },
    toString: function () {
        return "[object JSVertex(from: " +
            this.from + ", cost: " + this.cost + ")]";
    },
    // this is a custom method not defined in any Java class or Interface
    calculate: function (to) {
        return Math.abs(+to - this.from) * this.cost;
    }
};

var wrapJso = (function () {
    var
        JObjExtender = Java.extend(Java.type(
            "jdk.nashorn.api.scripting.AbstractJSObject")),
        _getMember = function (name) {
            return this.jso[name];
        },
        _setMember = function (name, value) {
            this.jso[name] = value;
        },
        _toString = function () {
            return this.jso.toString();
        };

    return function (jsObject) {
        var F = function () {
        };
        F.prototype = jsObject;
        var f = new F();
        f.jso = jsObject;
        f.getMember = _getMember;
        f.setMember = _setMember;
        f.toString = _toString; // "toString hack" - explained later
        return new JObjExtender(f);
    };
})();

function LogTest2() {
    var wrapped = wrapJso(new JSVertex(11, 12));

// access custom js property and method not defined in any java class
// or interface.
    print(wrapped.from);
    print(wrapped.calculate(17));

    print("--------------");

// call toString() and hashCode() from JavaScript on wrapper object
    print(wrapped.toString());
    print(wrapped.hashCode());

    print("--------------");

// Use StringBuilder to make Java call toString() on our wrapper object.
    print(new java.lang.StringBuilder().append(wrapped).toString());
// see hack in wrapJso() - for some reason java does not see
// overriden toString if it is defined as prototype member.

// Do some operations on HashMap to get hashCode() mehod called from java
    var map = new java.util.HashMap();
    map.put(wrapped, 10);
    map.get(wrapped);

    wrapped.from = 77;
    map.get(wrapped);

    print("--------------");

// let's show that modyfing any of pair: wrapped or jso touches underlying jso.
    var jso = new JSVertex(17, 128);
    wrapped = wrapJso(jso);
    print(wrapped);
    jso.from = 9;
    wrapped.cost = 10;
    print(wrapped);
    print(jso);
    print(jso == wrapped);
}