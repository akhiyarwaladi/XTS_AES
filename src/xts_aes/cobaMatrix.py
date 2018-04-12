
# coding: utf-8

# In[1]:


list1 = [('<start>', 'BD'), ('BD', 'KR'), ('KR', 'KR'), ('KR', 'XX'), ('XX', 'BD'), ('BD', 'XX'), ('XX', 'XX'), ('XX', 'BD'), ('BD', 'XX'), ('XX', 'XX'), ('<start>', 'BD'), ('BD', 'XX'), ('XX', 'KR'), ('KR', 'BD'), ('BD', 'XX'), ('XX', 'XX'), ('XX', 'XX'), ('<start>', 'XX'), ('XX', 'SF'), ('SF', 'BD'), ('BD', 'KR'), ('KR', 'XX'), ('XX', 'BD'), ('BD', 'XX'), ('XX', 'BD'), ('<start>', 'XX'), ('XX', 'BD'), ('BD', 'KR'), ('KR', 'KR'), ('KR', 'KR'), ('<start>', 'XX'), ('XX', 'KR'), ('KR', 'XX'), ('XX', 'BD'), ('BD', 'XX'), ('XX', 'BD'), ('BD', 'XX'), ('XX', 'BD'), ('<start>', 'BD'), ('BD', 'XX'), ('XX', 'KR'), ('KR', 'BD'), ('BD', 'SF'), ('SF', 'BD'), ('BD', 'BD'), ('BD', 'BD'), ('<start>', 'XX'), ('XX', 'BD'), ('BD', 'KR'), ('KR', 'XX'), ('XX', 'SF'), ('SF', 'XX'), ('XX', 'BD'), ('<start>', 'BD'), ('BD', 'XX'), ('XX', 'BD'), ('BD', 'XX'), ('XX', 'BD'), ('BD', 'BD'), ('BD', 'KR'), ('KR', 'XX'), ('XX', 'XX'), ('XX', 'XX'), ('XX', 'BD')]

dictCount = {}
for i in list1:
    if(i not in dictCount):
        dictCount[i] = 1
    else:
        dictCount[i] += 1


# In[2]:


import pandas as pd

liState = []
for i in dictCount:
    if(i[0] not in liState):
        liState.append(i[0])
        
liSt = list(reversed(liState))
dat = pd.DataFrame()
dat['index'] = liSt
dat = dat.set_index("index")
for i in liSt:
    dat[i] = 0
    
dat = dat.drop("<start>", 1)


# In[3]:


for index, row in dat.iterrows():
    
    for column in dat:
        
        tupIter = (index,column)
        print (tupIter)
        if(tupIter in dictCount):
            
            dat.ix[index,column] = dictCount[tupIter]
        else:
            
            dat.ix[index,column] = 0



# In[4]:


print(dat)

