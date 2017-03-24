permtest<-function(nrep){
  healthy<-c(1.2, 3.3, 1.6, 2.3, 2.5, 2.7, 2.3, 1.5)
  n<- 8
  disease<-c(1.1, 0.5, 1.8, 2.0, 1.3, 1.2, 1.3, 0.3, 1.3, 2.4)
  m<- 10
  t.obs<-t.test(healthy,disease,alternative = "greater")$statistic
  pool<-c(healthy,disease)
  nsuccess<-0
  for (i in 1:nrep) {
    index<-sample(pool) # re-sample
    h<-index[1:n] # new healthy
    d<-index[(n+1):(n+m)] # new disease
    t.ran<-t.test(h, d, alternative = "greater")$statistic # get t.rand value
    if(abs(t.ran) > abs(t.obs)) {
      nsuccess<-nsuccess+1
    }
  }
  return (nsuccess/nrep)
}


precisioninterval<-function(n,nrep){
  # n = # of times calculating permtest(nrep)
  temp<-rep(0, n)
  for (i in 1:n){
    temp[i]<-permtest(nrep)
  }
  count<-0
  # count how many p's within mean(p) +- 0.01
  for (j in 1:n){
    if ( temp[j]<= mean(temp)+0.01 && temp[j] >= mean(temp)-0.01){
      count<-count+1
    }
  }
  print(mean(temp))
  return (count/n) # 0<= count/n <= 1
}

precisioninterval(100,1500)
# [1] 0.017480023
# [1] 1
precisioninterval(500,1500)
# [1] 0.01724533
# [1] 0.998


