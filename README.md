# guiscrot

Cross-platform screenshot capture tool, inspired much by [Sharex](https://github.com/ShareX/ShareX) which sadly is not cross-platform

## Requirements

- [pluginlib](https://github.com/redpois0n/pluginlib)
- [iconlib](https://github.com/redpois0n/iconlib)

## How to build

Compile from source into an runnable JAR with [pyjar](https://github.com/redpois0n/pyjar)

Put pyjar.py in the parent directory of the downloaded source and run

```
python pyjar.py [--jdk /path/to/jdk/bin/] [--classpath pluginlib.jar] --input src --output guiscrot.jar --mainclass com.redpois0n.guiscrot.Main
```
_path separator depends on system, ; for Windows, : for *nix_

*--jdk argument is only needed if javac isn't in your __$PATH__*

Then you can either double click the JAR or run it using

```
java -jar guiscrot.jar
```
