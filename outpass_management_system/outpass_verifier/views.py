# from django.shortcuts import render
# from django.http import HttpResponse
# from django.template import loader

# def verify(request):
#    template = loader.get_template('outpass_verifier/form.html')
#    context = {
        
#     }
#    return HttpResponse(template.render(context, request))
#    # return HttpResponse("Hello, world. You're at the polls index.")
# # Create your views here.

from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader

from .forms import ValidForm

def verify(request):
   if request.method == 'POST':
        # create a form instance and populate it with data from the request:
      form = ValidForm(request.POST)
        # check whether it's valid:
      if form.is_valid():
            # process the data in form.cleaned_data as required
            # ...
            # redirect to a new URL:
         return HttpResponse("Hello, world. You're at the polls index.")

    # if a GET (or any other method) we'll create a blank form
   else:
      form = ValidForm()

   return render(request, 'outpass_verifier/form.html', {'form': form})
   # return HttpResponse("Hello, world. You're at the polls index.")
# Create your views here.

