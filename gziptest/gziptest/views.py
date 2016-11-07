import json
from django.http import HttpResponse


def withgzip(request):
    data = json.dumps({'a': range(0,1000)})
    return HttpResponse(data, content_type='application/json')

def withoutgzip(request):
    data = json.dumps({})
    return HttpResponse(data, content_type='application/json')

