# Deploy cassandra service and sprint boot project on k8s
- Attach terminal to minikube docker environment
```bash
eval $(minikube docker-env)
```

- Build this spring boot project and create docker image
```bash
gradle build docker # Equals to gradle build + gradle docker
```
> - Then you can see the docker image: qiusuo/cassandra-demo:v1
```bash
  docker images
```

- Deploy cassandra service and spring boot project on k8s
```bash
kubectl create -f ./deploy/
```

- Test data by web control
```bash
kubectl port-forward svc/qiusuo/cassandra-demo-svc 8090:8090
```
Then visit http://localhost:8090/person/1 by your browser.




