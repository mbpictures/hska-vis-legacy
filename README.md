# Vis-lab mit einem Minikube-Cluster ausführen

Nachfolgend soll die Vorgehensweise zum deployen der Microservices mit k8s und istio als service-mesh beleuchtet werden.

## Voraussetzungen

- Ein beliebiges k8s-cluster und `kubectl` müssen installiert sein.

- `istioctl` muss installiert sein (sowie die sample/addons) [hier](https://github.com/hka-iwi-vislab/microservice-istio/blob/master/WIE-LAUFEN.md) erklärt.

## Deploy Kubernetes Cluster

```bash
kubectl apply -f microservice.yml
```

Mit diesem Befehl werden alle Deployments und services in Kubernetes erstellt und können anschließend bentutzt werden. Hierfür muss die externe IP-Addresse des Apache Servers extrahiert werden:

```bash
kubectl get services
```

So kann mittels ```http://[IP-ADDRESS]:80/[SERVICE_NAME]``` auf die microservices zugegriffen werden. Ein Beispiel Aufruf an den Products service könnte wie folgt lauten:

```HTTP
GET http://localhost/product/products
Accept: application/json
```

## Service-Mesh mit Istio erstellen

```bash
kubectl apply -f istioconf.yml
```

Dieser Befehl erzeugt die IngressGateways von Istio samt der virtuellen Services. Anschließend müssen noch die standard Addons (kiali, grafana, prometheus, ...) installiert werden:

```bash
kubectl apply -f samples/addons
```

Der nun obsolete Apache service kann gelöscht werden, da dessen Aufgabe vom IngressGateway übernommen wird:

```bash
kubectl delete deployments apache
kubectl delete service apache
```

Anfragen müssen nun an die IP-Addresse des Istio IngressGateways gestellt werden, die mit folgendem Befehl extrahiert werden kann:

```bash
kubectl get services -n istio-system
```

Um kiali, grafana und prometheus nutzen zu können, muss die automatische Sidecar injection aktiviert und anschließend die microservices neu deployed weren:
```bash
kubectl label namespace default istio-injection=enabled --overwrite
```

Um die Istio Dienste wie Grafana oder Prometheus zu nutzen, müssen die Ports der Dienste weitergeleitet werden. Dazu muss zunächst der pod Name extrahiert werden:

```bash
kubectl get pods -n istio-system
```

Und anschließend der port weitergeleitet werden:

```bash
kubectl port-forward -n istio-system [prometheus-pod-name] [port]:[port]
```

Nun kann der Dienst mit ```http://localhost:[port]``` genutzt werden.

Das kiali Dashboard kann mit dem Befehl ```istioctl dashboard kiali``` gestartet werden.