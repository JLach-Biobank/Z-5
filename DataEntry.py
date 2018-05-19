from __future__ import print_function
import numpy as np
import os


# to make this notebook's output stable across runs
np.random.seed(42)

BIODATA_PATH="/home/wlos/Biohackaton 2018/"

import numpy as np
import pandas as pd
import sklearn.linear_model

import pandas as pd

def encodePair(p1, p2):
    pairValueMap={"AA" : 1, "TT" : 2, "CC" : 3, "GG" : 4,
          "AT" : 5, "AC" : 6, "AG" : 7,
          "TA" : 8, "TC" : 9, "TG" : 10,
          "CA" : 11, "CT" : 12, "CG": 13,
          "GA": 14, "GC" : 15, "GT": 16,
            "II" : 0, "00" : 0, "DD" : 0, "DI" : 0, "ID" : 0}
    pair=p1+p2
    #print("Pair " + pair)
    val = pairValueMap[pair]
    #print (pair, val)
    return val


# Take HACK.ped file and turn into csv before you start
# cat HACK_15_one.ped | tr ' ' ',' > HACK.ped.all.csv
def convert_data(path=BIODATA_PATH):
    csv_path = os.path.join(path, "HACK.all.csv")
    csv_path_fixed=os.path.join(path, "HACK.all.fixed.csv")
    lineNo=1

    with open(csv_path) as fin:
        with open(csv_path_fixed, "w") as fout:
            for line in fin:
                print("#######################")
                #if (lineNo==1):
                #    lineNo+=1
                #    continue
                entry = line.rstrip().split(',')
                print (entry)
                print ("Number of nukleotides %d" % len(entry))
                print ("lineNo %d" % lineNo)
                print ("FID {%s}" % entry[0])
                print ("SampleId %s" % entry[1])
                print ("Sex %s" % entry[4] )
                print ("Affection %s" % entry[5])
                print (line)
                lineNo+=1
                header=entry[0]+"," +entry[1]+ ","+entry[4] +","+entry[5]
                print(header)
                fout.write(header)
                for i in range(6,len(entry)-2,2):
                    fout.write(str(encodePair(entry[i], entry[i+1])) +",")

                fout.write(str(encodePair(entry[-1], entry[-2])) )
                fout.write("\n")






data=convert_data("newdata")

print ("done")

