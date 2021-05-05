package com.amplifyframework.datastore.generated.model;

import android.content.res.Resources;
import android.icu.text.UnicodeSetIterator;

import com.edhou.taskmaster.R;

/** Auto generated enum from GraphQL schema. */
@SuppressWarnings("all")
public enum Status {
  NEW,
  IN_PROGRESS,
  ASSIGNED,
  COMPLETE;

  static public String getString(Status status, Resources resources) {
    switch (status) {
      case NEW: return resources.getString(R.string.status_new);
      case IN_PROGRESS: return resources.getString(R.string.status_in_progress);
      case ASSIGNED: return resources.getString(R.string.status_assigned);
      case COMPLETE: return resources.getString(R.string.status_complete);
    }
    return null;
  }
}
