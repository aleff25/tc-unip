import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import 'rxjs/add/operator/map';
import {Exercise} from './exercise.model';
import {UIService} from '../shared/ui.service';
import {HttpClient} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {map} from 'rxjs/operators';

@Injectable()
export class TrainingService {

  baseUrl = 'http://localhost:8080';
  exerciseChanged = new Subject<Exercise>();
  exercisesChanged = new Subject<Exercise[]>();
  finishedExercisesChanged = new Subject<Exercise>();
  private availableExercises: Exercise[] = [];
  private runningExercise: Exercise;
  private fbSubs: Subscription[] = [];

  constructor(private http: HttpClient, private uiService: UIService) {
  }

  fetchAvailableExercises() {
    this.uiService.loadingStateChanged.next(true);
    const resourceUrl = `${this.baseUrl}/exercises/available`;
    this.fbSubs.push(this.http.get(resourceUrl)
      .subscribe((exercises: Exercise[]) => {
        this.uiService.loadingStateChanged.next(false);
        this.availableExercises = exercises;
        this.exercisesChanged.next([...this.availableExercises]);
      }, error => {
        this.uiService.loadingStateChanged.next(false);
        this.uiService.showSnackbar('Fetching Exercises failed, please try again later', null, 3000);
        this.exercisesChanged.next(null);
      }));
  }

  startExercise(selectedId: string) {
    this.runningExercise = this.availableExercises.find(
      ex => ex.id === selectedId
    );
    this.exerciseChanged.next({...this.runningExercise});
  }

  completeExercise() {
    this.addDataToDatabase({
      ...this.runningExercise,
      date: new Date(),
      state: 'COMPLETED'
    });
    this.finishedExercisesChanged.next(this.runningExercise);
    this.runningExercise = null;
    this.exerciseChanged.next(null);
  }

  cancelExercise(progress: number) {
    this.addDataToDatabase({
      ...this.runningExercise,
      duration: this.runningExercise.duration * (progress / 100),
      calories: this.runningExercise.calories * (progress / 100),
      date: new Date(),
      state: 'CANCELLED'
    });
    this.finishedExercisesChanged.next(this.runningExercise);
    this.runningExercise = null;
    this.exerciseChanged.next(null);
  }

  getRunningExercise() {
    return {...this.runningExercise};
  }

  fetchCompletedOrCancelledExercises() {
    const resourceUrl = `${this.baseUrl}/exercises/finished`;
    return this.http.get(resourceUrl);
  }

  cancelSubscriptions() {
    this.fbSubs.forEach(sub => sub.unsubscribe());
  }

  private addDataToDatabase(exercise: Exercise) {
    const resourceUrl = `${this.baseUrl}/exercises/finished`;
    this.fbSubs.push(this.http.post(resourceUrl, exercise)
      .subscribe(res => res));
  }
}
