from audioop import reverse
from telnetlib import LOGOUT
from django.http import HttpResponseRedirect, JsonResponse


def check_user_authentication(request):

    services_cookie = request.COOKIES.get('services')
    # User will be deleted after the cookie expires.
    if not services_cookie:
        LOGOUT(request)
        return HttpResponseRedirect(reverse('index'))
    
    return JsonResponse({"success": True, "message": "No services user"})