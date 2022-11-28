from django import forms

class ValidForm(forms.Form):
    enroll_num = forms.CharField(label='Enrollment Number', max_length=100)