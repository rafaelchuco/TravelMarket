from rest_framework import viewsets, filters
from .models import Activity
from .serializers import ActivitySerializer

class ActivityViewSet(viewsets.ModelViewSet):
    queryset = Activity.objects.all()
    serializer_class = ActivitySerializer
    filter_backends = [filters.SearchFilter, filters.OrderingFilter]
    search_fields = ['name']
    ordering_fields = ['price_per_person', 'duration_hours']

    def get_queryset(self):
        queryset = super().get_queryset()

        destination = self.request.query_params.get('destination')
        activity_type = self.request.query_params.get('activity_type')
        difficulty_level = self.request.query_params.get('difficulty_level')

        if destination:
            queryset = queryset.filter(destination_id=destination)
        if activity_type:
            queryset = queryset.filter(activity_type=activity_type)
        if difficulty_level:
            queryset = queryset.filter(difficulty_level=difficulty_level)

        return queryset
