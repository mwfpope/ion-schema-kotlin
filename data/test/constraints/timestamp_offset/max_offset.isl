type::{
  timestamp_offset: ["+23:59"],
}
valid::[
  2000-01-01T00:00:00+23:59,
]
invalid::[
  2000-01-01T00:00:00+23:58,
  2000-01-01T00:00:00-23:59,
]

type::{
  timestamp_offset: ["-23:59"],
}
valid::[
  2000-01-01T00:00:00-23:59,
]
invalid::[
  2000-01-01T00:00:00-23:58,
  2000-01-01T00:00:00+23:59,
]

