HOWTO extract copyright information
===================================

The following commands gather all the tab-delimited text files that were 
generated with the annotation processors during the process-class phase:

* Mixed copyright:

```
ZIP="/tmp/copyright-mixed.zip"; rm -f $ZIP; zip -j $ZIP `find -name "*copyright-mixed*" | grep -v "checkout"`
```

* 3rd-party copyright:

```
ZIP="/tmp/copyright-3rdparty.zip"; rm -f $ZIP; zip -j $ZIP `find -name "*copyright-3rd*" | grep -v "checkout"`
```

$Revision$
